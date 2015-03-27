import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.traversal.InitialBranchState;

import scala.util.control.Exception.Finally;


public class HelloWorld {
	private static final String DB_PATH = "testDB";
	private static enum RelTypes implements RelationshipType{
		KNOWS
	}
	public static void main(String[] args) {  
        HelloWorld hw = new HelloWorld();  
        hw.run();  
    }  
	GraphDatabaseService graphDb;
	Node firstNode;
	Node secondNode;
	Relationship relationship;
	public void run()
	{		
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		registerShutdownHook( graphDb );
		Transaction tx = graphDb.beginTx();
		try {
			firstNode = graphDb.createNode();
			firstNode.setProperty("message", "Hello");
			secondNode = graphDb.createNode();
			secondNode.setProperty("message", "World");
			relationship = firstNode.createRelationshipTo(secondNode, RelTypes.KNOWS);
			relationship.setProperty("message", "I'm a relation");
			  	System.out.println(firstNode.getProperty("message"));  
		        System.out.println(relationship.getProperty("message"));  
		        System.out.println(secondNode.getProperty("message"));  
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			tx.finish();
			graphDb.shutdown();
		}
	}
	private static void registerShutdownHook(final GraphDatabaseService graphDb) {
		  Runtime.getRuntime().addShutdownHook(  
	                new Thread(){  
	                    @Override  
	                    public void run(){  
	                        graphDb.shutdown();  
	                    }  
	                }  
	            );  
		
	}
}
