/*
 * Copyright (c) 2014 conLeos GmbH. All Rights reserved.
 *
 * This software is the confidential intellectual property of conLeos GmbH;
 * it is copyrighted and licensed.
 */

package me.nax.pvalid.model.helper;

import me.nax.pvalid.model.P;

public class Exclude
  extends Combination
{
  public Exclude(P assertion, P source)
  {
    super(assertion, source);
  }
}
