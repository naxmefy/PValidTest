
package me.nax.pvalid.model.fail;

import me.nax.pvalid.model.helper.Combination;

public class Fail
{
  private static final String NORMALMESSAGE = "%s %s by %s but is also %s from %s";
  private static final String CIRCULARMESSAGE = "%s %s hisself";

  private Combination source;
  private Combination problem;
  private FailTyp failTyp;
  private boolean circular;

  public Fail(Combination source, Combination problem, FailTyp failTyp)
  {
    this(source, problem, failTyp, false);
  }

  public Fail(Combination source, Combination problem, FailTyp failTyp, boolean circular)
  {
    this.source = source;
    this.problem = problem;
    this.failTyp = failTyp;
    this.circular = circular;
  }

  public String toString()
  {

    String s;
    if(failTyp == FailTyp.INCLUSION)
    {
      s = failTyp + ": " + String.format(NORMALMESSAGE, source.getTarget(), "excluded", source.getSource(), "excluded", problem.getSource());
    }
    else
    {
      s = failTyp + ": " + String.format(NORMALMESSAGE, source.getTarget(), "included", source.getSource(), "excluded", problem.getSource());
    }
    if(circular)
    {
      String failTypDescription = failTyp == FailTyp.INCLUSION ? "include" : "exclude";
      s = "CIRCULAR: " + String.format(CIRCULARMESSAGE, source.getTarget(), failTypDescription);
    }
    return s;
  }

  public Combination getSource()
  {
    return source;
  }

  public void setSource(Combination source)
  {
    this.source = source;
  }

  public Combination getProblem()
  {
    return problem;
  }

  public void setProblem(Combination problem)
  {
    this.problem = problem;
  }

  public FailTyp getFailTyp()
  {
    return failTyp;
  }

  public void setFailTyp(FailTyp failTyp)
  {
    this.failTyp = failTyp;
  }

  public boolean isCircular()
  {
    return circular;
  }

  public void setCircular(boolean circular)
  {
    this.circular = circular;
  }
}
