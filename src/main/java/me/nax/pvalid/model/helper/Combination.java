
package me.nax.pvalid.model.helper;

import me.nax.pvalid.model.P;

public class Combination
{
  private P target;
  private P source;

  public Combination(P target, P source)
  {
    this.target = target;
    this.source = source;
  }

  public P getTarget()
  {
    return target;
  }

  public void setTarget(P target)
  {
    this.target = target;
  }

  public P getSource()
  {
    return source;
  }

  public void setSource(P source)
  {
    this.source = source;
  }
}
