package Logic;
import GameComponents.Board;
import GameComponents.Players.Ghost;

import java.util.*;

public class AStar {

    public static Stack search(Ghost ghost, Board board){

        //Initialize non-Walled Cells as nodes.
        Node[][] nodes = new Node[32][32];
        for (int i=0;i<32;i++){
            for (int j=0;j<32;j++){
                if (!board.getPieces()[i][j].isWall())
                    nodes[i][j]=new Node(i,j,1);
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
                    if (up) {
                        edge1 = new Edge(nodes[i - 1][j], 1);
                        edges.push(edge1);
                    }
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
                    nodes[i][j].adjacencies = new Edge[edges.size()];
                    for (int l=0;l<nodes[i][j].adjacencies.length;l++){
                        nodes[i][j].adjacencies[l]=edges.pop();
                    }
                }
            }
        }
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

        AstarSearch(nodes[myX][myY],nodes[destX][destY]);

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
        //Collections.reverse(path);
        return moves;
    }

    private static void AstarSearch(Node source, Node goal){

        Set<Node> explored = new HashSet<>();

        //override compare method
        PriorityQueue<Node> queue = new PriorityQueue<>(20,
                Comparator.comparingDouble(i -> i.f_scores)
        );

        //cost from start
        source.g_scores = 0;

        queue.add(source);

        boolean found = false;

        while((!queue.isEmpty())&&(!found)){

            //the node in having the lowest f_score value
            Node current = queue.poll();

            explored.add(current);

            //goal found
            if(current.x==goal.x&&current.y==goal.y){
                found = true;
            }

            //check every child of current node
            for(Edge e : current.adjacencies){
                Node child = e.target;
                double cost = e.cost;
                double temp_g_scores = current.g_scores + cost;
                double temp_f_scores = temp_g_scores + child.h_scores;


                                /*If child node has been evaluated and
                                the newer f_score is NOT higher, skip. If child node is not in queue or
                                newer f_score is lower, Execute.*/

                if(((!queue.contains(child))||(temp_f_scores < child.f_scores))&&!((explored.contains(child))&&(temp_f_scores >= child.f_scores))){

                    child.parent = current;
                    child.g_scores = temp_g_scores;
                    child.f_scores = temp_f_scores;

                    if(queue.contains(child)){
                        queue.remove(child);
                    }

                    queue.add(child);

                }
            }
        }
    }
}

class Node{

    public final int x,y;
    public double g_scores;
    public final double h_scores;
    public double f_scores = 0;
    public Edge[] adjacencies;
    public Node parent;

    Node(int x, int y, double hVal){
        this.x = x;
        this.y = y;
        h_scores = hVal;
    }

}

class Edge{
    public final double cost;
    public final Node target;

    Edge(Node targetNode, double costVal){
        target = targetNode;
        cost = costVal;
    }
}
