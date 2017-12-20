/**
 * Created with IntelliJ IDEA.
 * User: ndiaz
 * Date: 2013-10-15
 * Time: 13:37
 * To change this template use File | Settings | File Templates.
 */
import java.io.*;
import java.util.*;
import fuzzydl.*;
import fuzzydl.exception.*;
import fuzzydl.milp.*;
import fuzzydl.parser.*;


public class FuzzyDL
{
    public static void main(String[] args) throws FuzzyOntologyException,
            IOException, ParseException, InconsistentOntologyException
    {
        try{
// Load options for the reasoner, using file "CONFIG"
        ConfigReader.loadParameters("CONFIG", new String[0]);
// Option 1. Create KnowledgeBase and queries from scratch
        // KnowledgeBase kb = new KnowledgeBase();
// Add axioms to the KnowledgeBase. Example: kb.addAssertion(...);

// Define queries:
        Concept c = new Concept("Person");
        Concept c1 = new Concept("Employer");
        Concept c2 = new Concept("Employee");
        Individual a = new Individual("Employee");
        Individual b = new Individual("Employer");
        Query q = new MinSatisfiableQuery(c);
        // KB satisfiability
        Query q1 = new KbSatisfiableQuery();
// Greatest lower bound of a concept assertion
        Query q2 = new MinInstanceQuery( c,  a);
        Query q3 = new MaxInstanceQuery( c,  a);
// Greatest lower bound of a concept for every individual
// Use methods getIndividuals and getDegrees to loop over the solutions
        Query q4 = new AllInstancesQuery( c);
// Greatest lower bound of a role assertion
        Query q5 = new MinRelatedQuery( a,  b, "isSiblingWith");
        Query q6 = new MaxRelatedQuery( a,  b, "isSiblingWith");
// Concept subsumption
        Query q7 = new MinSubsumesQuery( c1,  c2, MinSubsumesQuery.LUKASIEWICZ);
        Query q8 = new MinSubsumesQuery( c1,  c2, MinSubsumesQuery.GODEL);
        Query q9 = new MinSubsumesQuery( c1,  c2, MinSubsumesQuery.ZADEH);
        Query q10 = new MaxSubsumesQuery( c1,  c2, MaxSubsumesQuery.LUKASIEWICZ);
        Query q11 = new MaxSubsumesQuery( c1,  c2, MaxSubsumesQuery.GODEL);
        Query q12 = new MaxSubsumesQuery( c1,  c2, MaxSubsumesQuery.ZADEH);
// Concept satisfiability
        Query q13 = new MinSatisfiableQuery( c);
        Query q14 = new MinSatisfiableQuery( c,  a);
        Query q15 = new MaxSatisfiableQuery( c);
        Query q16 = new MaxSatisfiableQuery( c,  a);
// Deffuzification     (See aggressiveness example)
//        LomDefuzzifyQuery LOMq = new LomDefuzzifyQuery(Concept c, Individual i, String fName);
//        SomDefuzzifyQuery SOMq = new SomDefuzzifyQuery(Concept c, Individual i, String fName);
//        MomDefuzzifyQuery MOMq = new MomDefuzzifyQuery(Concept c, Individual i, String fName);

///////////////////////////////////////////////////////////////

// Option 2. Read knowledge base and queries from file "fileName.txt"
        Parser parser = new Parser(new FileInputStream("food-wine.txt"));
        //Parser parser1 = new Parser(new FileInputStream("multilevel-activities.txt"));
        //Parser parser2 = new Parser(new FileInputStream("aggressivenessFuzzyControl.txt"));
        //parser.Start();
        //KnowledgeBase kb = parser.getKB();
        //KnowledgeBase kb8 = parser1.getKB();
        //KnowledgeBase kb9 = parser2.getKB();
// The three latter lines can be replaced by the following one
        KnowledgeBase kb = Parser.getKB("food-wine.txt");
        //KnowledgeBase kb1 = Parser.getKB("aggressivenessFuzzyControl.txt");
        //KnowledgeBase kb2 = Parser.getKB("multilevel-activities.txt");
// Queries were also part of the file "fileName.txt"
        ArrayList <Query> queries = parser.getQueries();
// After having created KB and queries, start logical inference
        kb.solveKB();
// Solve a query q
        Solution result = q.solve(kb);
// Print the result
        if (result.isConsistentKB())
            System.out.println(q.toString()+ result.getSolution());
// In AllInstancesQuery: System.out.println(q.toString());
        else {
            System.out.println("KB is inconsistent");
        }
// Optionally, show the time and language of the KB
        System.out.println("Time (s): ".concat(String.valueOf(q.getTotalTime())));
        System.out.println("Language: ".concat(kb.getLanguage()));


        //Getting an individual
        Individual wine = kb.getIndividual("MountadamChardonnay");

        // Concept implication (default, Goedel, Lukasiewicz and Kleene-Dienes)
        //Concept.implies( c1,  c);
        Concept.gImplies( c1,  c);
        Concept.lImplies( c1,  c);
        Concept.zImplies( c1,  c);

        //fuzzyDL can show the values in an optimal solution.
// Show the value of a variable
//        kb.milp.showVars.addVariable( var,  varName);
//// Show the value of all fillers of a concrete role
//        kb.milp.showVars.addConcreteFillerToShow( roleName);
//// Show the value of the concrete filler of some individual
//        kb.milp.showVars.addConcreteFillerToShow( roleName,  indName);
//// Show the membership to the concepts of all the fillers
//// of an abstract role
//
//        kb.milp.showVars.addAbstractFillerToShow( roleName);
//// Show the membership to the concepts of all the fillers
//// of an abstract role for some individual
//        kb.milp.showVars.addAbstractFillerToShow( roleName,  indName);
//// Show all instances of an atomic concept
//        kb.milp.showVars.addConceptToShow( conceptName);
//// Show all atomic concepts of an individual
//        kb.milp.showVars.addIndividualToShow( indName);

    }catch (Exception e){//Catch exception if any
        System.err.println("Error reading dataset file: " + e.getMessage());
    }
    }


    // Concrete fuzzy concrete concepts
// (Use them only in existential and universal restrictions)
    /*CrispConcreteConcept ccc = new CrispConcreteConcept(String conceptName, double k1, double k2,
                             double a, double b);
    new LeftConcreteConcept(String conceptName, double k1, double k2,
                            double a, double b);
    new RightConcreteConcept(String conceptName, double k1, double k2,
                                     double a, double b);
    new TriangularConcreteConcept(String conceptName, double k1, double k2,
                                  double a, double b, double c);
    new TrapezoidalConcreteConcept(String conceptName, double k1, double k2,
                                   double a, double b, double c, double d);
    new LinearConcreteConcept(String conceptName, double k1, double k2,
                              double c);
    new ModifiedConcreteConcept(String conceptName, LinearModifier mod,
                                FuzzyConcreteConcept fMod);*/


}

