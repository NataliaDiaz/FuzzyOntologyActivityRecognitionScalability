/**
 * Created with IntelliJ IDEA.
 * User: ndiaz
 * Date: 2013-11-21
 * Time: 11:47
 * To change this template use File | Settings | File Templates.
 */
import java.io.*;
import java.util.*;
import fuzzydl.*;
import fuzzydl.exception.*;
import fuzzydl.milp.*;
import fuzzydl.parser.*;
import fuzzydl.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RobinWSum {

    public static void main(String[] args) throws FuzzyOntologyException,
            IOException, ParseException
    {
        //try {
            //ConfigReader.loadParameters("D:/Software/Netbeans projects/Working/Kinect_Java/CONFIG", new String[0]);
            //Parser parser = new Parser(new FileInputStream("D:/Software/Netbeans projects/Working/Kinect_Java/Kinect_v2.txt"));
            // Load options for the reasoner, using file "CONFIG"
            ConfigReader.loadParameters("CONFIG", new String[0]);
            // Option 2. Read knowledge base and queries from file "fileName.txt"
            Parser parser = new Parser(new FileInputStream("Kinect_v2.txt"));
            parser.Start();
            KnowledgeBase kb = parser.getKB();
            kb.solveKB();



//            Example 1: Dening basic movement (Stand, BendDown, TwistRight, Move-Object, etc.) can be mapped to OWL 2, e.g., the Action Sitting, would be of the
// performsAction(Natalia; Sit) ^ hasStartDatetime(Sit; T ).


            Concept MoveObject = kb.getConcept("MoveObject");
            Concept Walk = kb.getConcept("Walk");

            Concept Room = kb.getConcept("Bottle");
            Concept SendMessage  = kb.getConcept("Plate");
            Concept WorkPosition = kb.getConcept("Fork");
            //  Concept Spoon  = kb.getConcept("Spoon");
            Concept Knife = kb.getConcept("Knife");
            Concept Sit = kb.getConcept("Sit");
            //     Concept Fork = kb.getConcept("fork");
            //   Concept Plate = kb.getConcept("Plate");
            Concept Bottle = kb.getConcept("Bottle");
            Concept User = kb.getConcept("User");

            Concept InUseFuzzy = kb.getConcept("InUseFuzzy");

            Concept Fork = Concept.some("usesFork", InUseFuzzy);
            Concept Spoon = Concept.some("usesSpoon", InUseFuzzy);
            Concept Plate = Concept.some("usesPlate", InUseFuzzy);


            Individual Natalia = kb.getIndividual("Natalia");
            Individual Robin = kb.getIndividual("Robin");

            ArrayList<Double> a = new ArrayList<Double>();
            a.add(0.2);
            a.add(0.6);
            a.add(0.2);

            ArrayList<Concept> b = new ArrayList<Concept>();
            b.add(Spoon);
            b.add(Plate);
            b.add(Fork);


            WeightedSumConcept Lunch= new WeightedSumConcept(a, b);

            //        OwaConcept Lunch= new OwaConcept(a, b);

            //      Concept performsAction = Concept.some("Natalia", Sit);
            //       Concept hasStartDatetime = Concept.some("T", Sit);

            //       Individual Action = kb.getIndividual("Action");

            //          ArrayList<Double> a = new ArrayList<>();
//		a.add(0.5);
            //             a.add(0.5);
            //   a.add(0.3);

            //           ArrayList<Concept> b = new ArrayList<>();
//		b.add(performsAction);
//		b.add(hasStartDatetime);
            //     b.add(WorkPosition);

//            WeightedSumConcept Test1 = new WeightedSumConcept(a, b);

            //      (define-concept Dinner (and Person (w-sum (0.2 Bottle) (0.3 Plate) (0.2 Fork) (0.2 Spoon) (0.1 Knife))))


            //      Query q = new KbSatisfiableQuery();         //       Sat!!!
            //      Query q = new AllInstancesQuery(Walk);    // Minsat for all to MoveObject!!

            //   Query q = new MinInstanceQuery(Test1, Action);




            Query q = new MinSatisfiableQuery(Lunch,Robin);
            Query q2 = new MinSatisfiableQuery(Lunch, Natalia);
            Query q3 = new AllInstancesQuery(Lunch);


            // Solve a query q

            // new KbSatisfiableQuery();

            Solution result = q.solve(kb);
            Solution result2 = q2.solve(kb);
            Solution result3 = q3.solve(kb);
            //   Solution result = q.solve(kb);
            // Print the result

            if (result.isConsistentKB())  {
                System.out.println(q.toString() + result.getSolution());
                System.out.println(q2.toString() + result2.getSolution());
                System.out.println(q3.toString() + result3.getSolution());
                // In AllInstancesQuery: System.out.println(q.toString());
            }
            else
                System.out.println("KB is inconsistent");
            // Optionally, show the time and language of the KB
            System.out.println("Time (s): " + q.getTotalTime());
            System.out.println("Language: " + kb.getLanguage());
        //} catch (InconsistentOntologyException ex) {
        //    Logger.getLogger(ClassUsingFuzzyDL.class.getName()).log(Level.SEVERE, null, ex);
        //}


    }

}
