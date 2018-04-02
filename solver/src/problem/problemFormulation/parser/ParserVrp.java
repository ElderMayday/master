package problem.problemFormulation.parser;

import problem.problemFormulation.ProblemVRP;

/**
 * Created by Aldar on 02-Apr-18.
 */
public abstract class ParserVrp
{
    public abstract void parseVRP(String text, ProblemVRP problem) throws Exception;
}