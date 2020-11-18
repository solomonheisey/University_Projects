import java.util.*;

class MST {

    private City[] predArray;
    private ArrayList<City> cities;

    //constructs MST
    //predArray stores predecessors
    MST (ArrayList<City> cities) {

        int V = cities.size();
        this.cities = cities;
        this.predArray = new City[V];
        boolean[] visited = new boolean[V];

        //Initialize key array and set all values to positive infinity
        double[] keyArray = new double[V];
        Arrays.fill(keyArray, Double.POSITIVE_INFINITY);

        //Sets starting vertex
        keyArray[0] = 0;

        //Loops V-1 times
        for(int iterator = 0; iterator < V - 1; iterator++) {

            //Finds the minimum key in the keyArray and sets the minCity to equal the city at the minIndex
            int minIndex = calculateMin(keyArray, visited);
            City minCity = cities.get(minIndex);
            visited[minIndex] = true;


            //Iterates through all adjacent cities and checks if the value in keyArray is the smallest value
            int i = 0;
            for (City adjacentCity : cities) {

                if ((keyArray[i] > minCity.distance(adjacentCity)) && !visited[i]) {
                    keyArray[i] = minCity.distance(adjacentCity);
                    this.predArray[i] = minCity;
                }
                i++;
            }
        }
    }

    //finds the minimum index of keyArray
    private int calculateMin(double[] keyArray, boolean[] visited) {
        int minimumIndex= -1;
        double minimumValue = Double.POSITIVE_INFINITY;

        for(int k = 0; k < keyArray.length; k++)
            if(keyArray[k] < minimumValue && !visited[k]) {
                minimumValue = keyArray[k];
                minimumIndex = k;
            }
        return minimumIndex;
    }

    //sums and takes sqrt of all cities and their predecessors
    double weight() {

        double distance = 0;

        for(int i = 1; i < predArray.length; i++)
            distance += Math.sqrt(this.cities.get(i).distance(predArray[i]));

        return distance;
    }

    ArrayList<City> tour() {

        Node root = tourRecursive(this.cities.get(0), this.predArray);
        ArrayList<City> tour = new ArrayList<>();
        computePreOrder(root, tour);
        tour.add(root.vertex);
        return tour;
    }

    private Node tourRecursive(City currCity, City[] predArray) {

        Node currNode = new Node();
        currNode.vertex = currCity;
        ArrayList<City> cityArrayList = new ArrayList<>();


        for(int i = 0; i < predArray.length; i++)
            if(predArray[i] == currCity)
                cityArrayList.add(cities.get(i));

        ArrayList<Node> childNodes = new ArrayList<Node>();

        for(City c2 : cityArrayList) {
            Node newNode = tourRecursive(c2, predArray);
            childNodes.add(newNode);
        }

        currNode.childNodes = childNodes;
        return currNode;
    }

    //Iterates through BT and adds elements to ArrayList in PreOrder
    private void computePreOrder(Node node, ArrayList<City> tour) {
        if(node.vertex == null) return;

        tour.add(node.vertex);

        for(Node currNode : node.childNodes)
            computePreOrder(currNode, tour);
    }
}

//Node class used to construct BT
class Node {
    City vertex;
    ArrayList<Node> childNodes;

    Node() {
        this.vertex = null;
        this.childNodes = null;
    }
}
