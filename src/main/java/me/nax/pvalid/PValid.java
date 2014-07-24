/*
 * Copyright (c) 2014 conLeos GmbH. All Rights reserved.
 *
 * This software is the confidential intellectual property of conLeos GmbH;
 * it is copyrighted and licensed.
 */

package me.nax.pvalid;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.nax.pvalid.model.fail.Fail;
import me.nax.pvalid.model.P;
import me.nax.pvalid.model.V;
import me.nax.pvalid.model.helper.Exclude;
import me.nax.pvalid.model.helper.Include;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class PValid
{
  public static void main(String[] args)
  {
    BufferedReader br = null;
    try
    {
      String path = PValid.class.getResource("/p.json").getPath();
      System.out.println(path);
      br = new BufferedReader(new FileReader(path));
      Gson gson = new Gson();
      Type type = new TypeToken<List<P>>()
      {
      }.getType();
      List<P> products = gson.fromJson(br, type);
      for(P product : products)
      {
        V v = new V(product, products);
        v.validate();

        if(v.getIncludes().size() > 0)
        {
          System.out.println("========================================================================");
          System.out.println("========================================================================");
          System.out.println(product.getId());
          System.out.println(product.getName());
          System.out.println("Incs: " + product.getIncludes().size());
          System.out.println("Excs: " + product.getExcludes().size());
          System.out.println("");
          System.out.println("");
          System.out.println(v.getProduct().getId());
          System.out.println(v.getProduct().getName());
          System.out.println("Incs: " + v.getIncludes().size());
          System.out.println("ExcsFails: " + v.getFails().size());
          System.out.println("");
          for(Include include : v.getIncludes())
          {
            System.out.println("Included: " + include.getTarget().getId() + "<" + include.getTarget().getName() + ">"
                               + " (by " + include.getSource().getId() + "<" + include.getSource().getName() + ">)");
          }
          System.out.println("");
          for(Exclude exclude : v.getExcludes())
          {
            System.out.println("Excluded: " + exclude.getTarget().getId() + "<" + exclude.getTarget().getName() + ">"
                               + " (by " + exclude.getSource().getId() + "<" + exclude.getSource().getName() + ">)");
          }
          System.out.println("========================================================================");
          System.out.println("Filtered Includes");
          for(Integer id : v.getFilteredIncludes())
          {
            Include filteredInclude = v.getIncludeFromSetForTargetId(id);
            System.out.println("Included: " + filteredInclude.getTarget().getId() + "<" + filteredInclude.getTarget().getName() + ">"
                               + " (by " + filteredInclude.getSource().getId() + "<" + filteredInclude.getSource().getName() + ">)");
          }
          System.out.println("========================================================================");
          System.out.println("Filtered Excludes");
          for(Integer id : v.getFilteredExcludes())
          {
            Exclude filteredExclude = v.getExcludeFromSetForTargetId(id);
            System.out.println("Included: " + filteredExclude.getTarget().getId() + "<" + filteredExclude.getTarget().getName() + ">"
                               + " (by " + filteredExclude.getSource().getId() + "<" + filteredExclude.getSource().getName() + ">)");
          }
          System.out.println("========================================================================");
          System.out.println("Fails");
          for(Fail fail : v.getFails())
          {
            System.out.println(fail);
          }
          System.out.println("========================================================================");
          System.out.println("========================================================================");
        }
      }
    }
    catch(FileNotFoundException e)
    {
      e.printStackTrace();
    }
  }
}
