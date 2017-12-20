import fuzzydl.*;
import fuzzydl.exception.FuzzyOntologyException;
import fuzzydl.exception.InconsistentOntologyException;
import fuzzydl.milp.Solution;
import fuzzydl.parser.ParseException;
import fuzzydl.parser.Parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: ndiaz
 * Date: 2013-11-22
 * Time: 15:44
 * To change this template use File | Settings | File Templates.
 */
public class FuzzyDL_FuzzyRules_v1{
   public static void main(String[] args) throws FuzzyOntologyException,IOException, ParseException, InconsistentOntologyException
   {
    try {

        // Load options for the reasoner, using file "CONFIG"
        ConfigReader.loadParameters("CONFIG", new String[0]);
        // Option 2. Read knowledge base and queries from file "fileName.txt"
        Parser parser = new Parser(new FileInputStream("C:/FuzzyOWL2Tools.2.05/FuzzyDL/NataliasRules.txt"));   //Kinect_v2.txt"));
        parser.Start();
        KnowledgeBase kb = parser.getKB();
        kb.solveKB();
        Concept Walk = kb.getConcept("Walk");

        Concept Spoon  = kb.getConcept("Spoon");
        //Concept Knife = kb.getConcept("Knife");
        Concept Fork = kb.getConcept("Fork");
        Concept Plate = kb.getConcept("Plate");
        Concept Bottle = kb.getConcept("Bottle");
        Concept User = kb.getConcept("User");
        Individual Natalia = kb.getIndividual("Natalia");
        Individual Robin = kb.getIndividual("Robin");
        //Individual JohanLiliusOffice = kb.getIndividual("JohanLiliusOffice");
        Concept JohanLiliusOffice = kb.getConcept("JohanLiliusOffice");
        // New Concepts
        //Concept Spoon = new Concept("Spoon");
        //Concept Plate = new Concept("Plate");
        //Concept Fork = new Concept("Fork");

        // Aggregation operators
        ArrayList<Double> weights = new ArrayList<Double>();
        weights.add(0.2);
        weights.add(0.6);
        weights.add(0.2);

        //Concept isUsingSpoon = Concept.some("isUsingObject", Spoon);
        //Concept isUsingPlate = Concept.some("isUsingObject", Plate);
        //Concept isUsingFork = Concept.some("isUsingObject", Fork);

        ArrayList<Concept> concepts = new ArrayList<Concept>();
        concepts.add(User);
        //concepts.add(isUsingSpoon);
        //concepts.add(isUsingPlate);
        //concepts.add(isUsingFork);

        WeightedSumConcept Lunch= new WeightedSumConcept(weights, concepts);


        //Rule 2
        ArrayList<Concept> axioms = new ArrayList<Concept>();
        axioms.add(User);
       //axioms.add(Concept.some("hasPhone"));
       //axioms.add(Concept.some("isInLocation")) ;
       //Concept antecedent =  Concept.and(User, Concept.and(Concept.some("hasPhone", Concept.and(Concept.some("isInLocation", Concept.some("isNearTo", JohanLiliusOffice))));
       // (define-concept antecedent (g-and User (some hasPhone (some isInLocation (some isNearTo JohanLiliusOffice)))) )

      // % Se pueden añadir conjuntivamente otros consecuentes Di
       //    (define-concept consequent3 (g-and Di (some performsActivity MeetingSupervisor)))

       //Concept.implies(Concept c1, Concept.and(User, Concept.some("performsActivity", Lunch));
            // Solve a query q
        Query startQuery = new KbSatisfiableQuery();
        Query q = new AllInstancesQuery(Lunch);
        Query q1 = new AllInstancesQuery(Lunch);//JohanNataliaMeeting);
        Query q2 = new AllInstancesQuery(Lunch);
        Query q3 = new AllInstancesQuery(Lunch);


        Solution startResult = startQuery.solve(kb);
        Solution result = q.solve(kb);
        Solution result1 = q1.solve(kb);
        Solution result2 = q2.solve(kb);
        Solution result3 = q3.solve(kb);
            // Solution result = q.solve(kb);
            // Print the result

        if (startResult.isConsistentKB())  {
            System.out.println(q.toString() + result.getSolution());
            System.out.println(q1.toString() + result1.getSolution());
            //System.out.println(q2.toString() + result2.getSolution());
            //System.out.println(q3.toString() + result3.getSolution());
        }
        else
            System.out.println("KB is inconsistent");
        // Optionally, show the time and language of the KB
        System.out.println("Time (s): ".concat(String.valueOf(q.getTotalTime())));
        System.out.println("Language: ".concat(kb.getLanguage()));

    //} catch (inconsistentontologyexception ex) {
    //    logger.getlogger(classusingfuzzydl.class.getname()).log(level.severe, null, ex);
    //}

    }
    catch (FuzzyOntologyException e)
    { System.out.println(e); }
    /*}catch (Exception e){//Catch exception if any
        System.err.println("Error reading dataset file: " + e.getMessage());
    }*/
}
}