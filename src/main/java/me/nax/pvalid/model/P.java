/*
 * Copyright (c) 2014 conLeos GmbH. All Rights reserved.
 *
 * This software is the confidential intellectual property of conLeos GmbH;
 * it is copyrighted and licensed.
 */

package me.nax.pvalid.model;

import java.util.ArrayList;
import java.util.List;

public class P
{
  private Integer id;
  private String name;
  private List<Integer> includes = new ArrayList<Integer>();
  private List<Integer> excludes = new ArrayList<Integer>();

  public P(Integer id, String name, List<Integer> includes, List<Integer> excludes)
  {
    this.id = id;
    this.name = name;
    this.includes = includes;
    this.excludes = excludes;
  }

  public String toString()
  {
    return this.getName();
  }

  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public List<Integer> getIncludes()
  {
    return includes;
  }

  public void setIncludes(List<Integer> includes)
  {
    this.includes = includes;
  }

  public List<Integer> getExcludes()
  {
    return excludes;
  }

  public void setExcludes(List<Integer> excludes)
  {
    this.excludes = excludes;
  }
}
