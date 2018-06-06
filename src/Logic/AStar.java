package Logic;
import GameComponents.Board;
import GameComponents.Players.Ghost;

import java.util.*;

public class AStar {

    public static Stack search(Ghost ghost, Board board){

        int destX,destY,myX,myY;
        if (ghost.isChasing()){
            destX = board.getPacman().getLocation()[0];
            destY = board.getPacman().getLocation()[1];
        } else {
            destX = 2;
            destY = 6;
        }
        myX = ghost.getLocation()[0];
        myY = ghost.getLocation()[1];
        //Gather Info Regarding What to Chase and current locations.

        //Initialize non-Walled Cell as a node.
        Node[][] nodes = new Node[32][32];
        int diffX,diffY,distance;
        for (int i=0;i<32;i++){
            for (int j=0;j<32;j++){
                if (!board.getPieces()[i][j].isWall()) {
                    diffX = i - destX;
                    diffY = j - destY;
                    distance = Math.abs(diffX*diffY);//Cheapest Possible Path.
                    nodes[i][j] = new Node(i, j, distance);
                }
            }
        }

        //initialize the edges
        Edge edge1;
        Edge edge2;
        Edge edge3;
        Edge edge4;
        Stack<Edge> edges = new Stack<>();
        for (int i=0;i<32;i++){
            for (int j=0;j<32;j++){
                if (nodes[i][j]!=null){
                    boolean up = !board.getPieces()[i-1][j].isWall(), right = !board.getPieces()[i][j+1].isWall(), down = !board.getPieces()[i+1][j].isWall(),left = !board.getPieces()[i][j-1].isWall();
                    //The line Above Checks for walls to make the Edge checking easy.
                    if (up) {
                        edge1 = new Edge(nodes[i - 1][j], 1);           //For Every Node, First Save it's Edges into a Stack.
                        edges.push(edge1);                                     //Second, Count The Edges we saved.
                    }                                                          //Third, Put the Edges into an Array in the needed size For later use.
                    if (right) {
                        edge2 = new Edge(nodes[i][j + 1], 1);
                        edges.push(edge2);
                    }
                    if (down) {
                        edge3 = new Edge(nodes[i + 1][j], 1);
                        edges.push(edge3);
                    }
                    if (left) {
                        edge4 = new Edge(nodes[i][j - 1], 1);
                        edges.push(edge4);
                    }
                    nodes[i][j].neighbors = new Edge[edges.size()];
                    for (int l=0;l<nodes[i][j].neighbors.length;l++){
                        nodes[i][j].neighbors[l]=edges.pop();
                    }
                }
            }
        }

        AstarSearch(nodes[myX][myY],nodes[destX][destY]);//Search for Shortest Path From nodes[myX][myY] to node [destX][destY].

        return route(nodes[destX][destY]);
    }

    private static Stack route(Node target){
        Stack<Node> path = new Stack<>();
        Stack<Integer> moves = new Stack<>();
        for(Node node = target; node!=null; node = node.parent){
            if (path.empty())
                path.push(node);
            else {
                if (node.x==path.peek().x+1&&node.y==path.peek().y)
                    moves.push(1);
                else if (node.x==path.peek().x&&node.y==path.peek().y-1)
                    moves.push(2);
                else if (node.x==path.peek().x-1&&node.y==path.peek().y)
                    moves.push(3);
                else if (node.x==path.peek().x&&node.y==path.peek().y+1)
                    moves.push(4);
                path.push(node);
            }
        }
        return moves;
    }

    private static void AstarSearch(Node source, Node goal){

        Set<Node> explored = new HashSet<>();//Set of Explored Nodes.


        PriorityQueue<Node> queue = new PriorityQueue<>(20,
                Comparator.comparingDouble(i -> i.f_scores)
        );//Create Comparator for Priority Queue.


        source.g_scores = 0;//Cost from start.

        queue.add(source);//Add the node representing the Tile THe Ghost on to the Queue.

        boolean found = false;

        while((!queue.isEmpty())&&(!found)){ //Keep Searching until A Path Was Found or the Priority Queue is Empty.


            Node current = queue.poll();//The node that has the lowest f_score value

            explored.add(current);//Add Current Node to Explored Set.


            if(current.x==goal.x&&current.y==goal.y){
                found = true;
            }//Check If Current Node is our destination.


            for(Edge e : current.neighbors){//check every neighbor of current node.
                Node child = e.target;//Neighbor Node.
                double cost = e.cost;//Travel Cost (Always 1 in our case).
                double temp_g_scores = current.g_scores + cost;//Temporarily Save the Travel Cost if Traveling to this node.
                double temp_f_scores = temp_g_scores + child.h_scores;//Temporarily Save the Estimated final Travel Cost if Traveling to this node.


                                /*If child node has been evaluated and
                                the newer f_score is higher, skip. If child node is not in queue or
                                newer f_score is lower, Execute.*/

                if(((!queue.contains(child))||(temp_f_scores < child.f_scores))&&!((explored.contains(child))&&(temp_f_scores >= child.f_scores))){

                    child.parent = current;
                    child.g_scores = temp_g_scores;
                    child.f_scores = temp_f_scores;

                    if(queue.contains(child)){
                        queue.remove(child);
                    }
                    queue.add(child);
                }//Add child to Queue with Updated Travel Values.
            }
        }
    }
}

class Node{

    public final int x,y;//Node location on Graph.
    public double g_scores;//Travel Cost so Far.
    public final double h_scores;//Heuristic value, Minimal Estimated distance to Destination from This node.
    public double f_scores = 0;//Total Travel Score.
    public Edge[] neighbors;//Node's Neighbors.
    public Node parent;//Node Parent on Path.

    Node(int x, int y, double hVal){
        this.x = x;
        this.y = y;
        h_scores = hVal;
    }

}

class Edge{
    public final double cost;//Travel Cost on this Edge.
    public final Node target;//Destination Node.

    Edge(Node targetNode, double costVal){
        target = targetNode;
        cost = costVal;
    }
}
