import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

class Vertex{
    private String name;
    private LinkedList<Edge> edgeList;

    public Vertex(String name){
        this.name = name;
        edgeList = new LinkedList<>();
    }

    public String getName(){
        return name;
    }

    public LinkedList<Edge> getEdges(){
        return edgeList;
    }
}

class Edge{
    private int weight;
    private Vertex destVertex;

    public Edge(Vertex dest, int w){
        this.destVertex = dest;
        this.weight = w;
    }

    /* can use this approach for an unweighted graph
        or better remove variable weight altogether from Edge class */
    public Edge(Vertex dest){
        this.destVertex = dest;
        this.weight = 1;
    }

    public int getWeight(){
        return weight;
    }

    public Vertex getDestVertex(){
        return destVertex;
    }
}

class directedGraph {
    private HashSet<Vertex> nodes;

    public directedGraph(){
        nodes = new HashSet<>();
    }

    public boolean AddEdge(Vertex v1, Vertex v2, int weight){
        //since it's a directed graph
        return v1.getEdges().add(new Edge(v2, weight));
        /* If you want to implement an undirected graph,
            add v2.getEdges().add(new Edge(v1, weight)) also */
    }

    public boolean AddVertex(Vertex v){
        return nodes.add(v);
    }

    public void printGraph(){
        //I printed it like this. You can print it however you want though
        for(Vertex v : nodes){
            System.out.print(v.getName() + ": ");
            for(Edge e : v.getEdges()){
                System.out.print("destVertex: " + e.getDestVertex().getName() + " weight: " + e.getWeight() + " | ");
            }
            System.out.print("\n");
        }
    }
}

public class airportSystem {
    public static void main(String[] args) throws IOException {

            File file = new File("/Users/jovannypayan/Downloads/prices.xlsx");   //creating a new file instance
            FileInputStream prices = new FileInputStream(file);   //obtaining bytes from the file
            XSSFWorkbook wb = new XSSFWorkbook(prices);
            XSSFSheet sheet = wb.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();    //iterating over excel file
            Row row = itr.next();
            Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column
            Cell cell;




        directedGraph ourGraph = new directedGraph();


        //vertices
        int x =0;
        ArrayList<String> vertices = new ArrayList<>();
        while (cellIterator.hasNext()) {
            cell = cellIterator.next();
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:    //field that represents string cell type
                    vertices.add(cell.getStringCellValue());
                    x++;
                    break;
                    default:
                }
        }

        //System.out.println(vertices);

        Vertex states[] = new Vertex[x];
        int i =0;
        for(Vertex element: states){
            states[i] = new Vertex(vertices.get(i));
            ourGraph.AddVertex(states[i]);
            i++;
        }



        ourGraph.AddEdge(v0, v1, 2);
        ourGraph.AddEdge(v1, v2, 3);
        ourGraph.AddEdge(v2, v0, 1);
        ourGraph.AddEdge(v2, v3, 1);
        ourGraph.AddEdge(v3, v2, 4);

        ourGraph.printGraph();

        for (i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            if (row != null) {
                for (int j = 1; j < row.getPhysicalNumberOfCells(); j++) {
                    cell = row.getCell(j);
                    if (cell != null) {
                        ourGraph.AddEdge(cell.getNumericCellValue()); // Do whatever operation you want here
                    }
                }
            }
        }
    }
}