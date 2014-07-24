package me.nax.pvalid.model;

import me.nax.pvalid.model.fail.Fail;
import me.nax.pvalid.model.fail.FailTyp;
import me.nax.pvalid.model.helper.Combination;
import me.nax.pvalid.model.helper.Exclude;
import me.nax.pvalid.model.helper.Include;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class V
{
  private P product;
  private List<P> products;
  private Set<Include> includes = new HashSet<Include>();
  private Set<Exclude> excludes = new HashSet<Exclude>();
  private Set<Fail> fails = new HashSet<Fail>();

  public V(P product, List<P> products)
  {
    this.product = product;
    this.products = products;
  }

  public P getProduct()
  {
    return product;
  }

  public void setProduct(P product)
  {
    this.product = product;
  }

  public List<P> getProducts()
  {
    return products;
  }

  public void setProducts(List<P> products)
  {
    this.products = products;
  }

  public Set<Include> getIncludes()
  {
    return includes;
  }

  public Set<Integer> getFilteredIncludes()
  {
    Set<Integer> filteredIncludes = new TreeSet<Integer>();
    for(Include include : includes)
    {
      filteredIncludes.add(include.getTarget().getId());
    }

    return filteredIncludes;
  }

  public Set<Integer> getFilteredExcludes()
  {
    Set<Integer> filteredExcludes = new TreeSet<Integer>();
    for(Exclude exclude : excludes)
    {
      filteredExcludes.add(exclude.getTarget().getId());
    }

    return filteredExcludes;
  }

  public void setIncludes(Set<Include> includes)
  {
    this.includes = includes;
  }

  public Set<Exclude> getExcludes()
  {
    return excludes;
  }

  public void setExcludes(Set<Exclude> excludes)
  {
    this.excludes = excludes;
  }

  public Set<Fail> getFails()
  {
    return fails;
  }

  public void setFails(Set<Fail> fails)
  {
    this.fails = fails;
  }

  //===========================================================================================================================================================
  //===========================================================================================================================================================
  public boolean validate()
  {
    if(product == null || products == null)
    {
      return true;
    }

    checkExcludes(product, products);
    checkIncludes(product, products);
    checkFails();
    return this.fails.size() == 0;
  }

  private void checkIncludes(P product, List<P> products)
  {
    for(Integer integer : product.getIncludes())
    {
      P tmpProduct = getProductFromList(integer);
      if(tmpProduct == null)
      {
        continue;
      }

      Include include = new Include(tmpProduct, product);
      if(!containsCombination(includes, include))
      {
        includes.add(include);
        checkExcludes(tmpProduct, products);
        checkIncludes(tmpProduct, products);
      }
    }
  }

  private void checkExcludes(P product, List<P> products)
  {
    for(Integer integer : product.getExcludes())
    {
      P tmpProduct = getProductFromList(integer);
      if(tmpProduct == null)
      {
        continue;
      }

      Exclude exclude = new Exclude(tmpProduct, product);

      if(!containsCombination(excludes, exclude))
      {
        excludes.add(exclude);
        checkExcludes(tmpProduct, products);
      }
    }
  }

  private void checkFails()
  {
    for(Include include : includes)
    {
      for(Exclude exclude : excludes)
      {
        // EXCLUSION FAILS
        // OR TARGET AND SOURCE ARE SAME
        if(exclude.getTarget().getId().equals(exclude.getSource().getId()))
        {
          addFail(new Fail(exclude, exclude, FailTyp.EXCLUSION, true));
        }
        // IF INCLUDE TARGET AND EXCLUDE TARGET IS SAME
        else if(include.getTarget().getId().equals(exclude.getTarget().getId()))
        {
          addFail(new Fail(include, exclude, FailTyp.EXCLUSION));
        }
      }

      //INCLUSION FAILS
      if(include.getTarget().getId().equals(include.getSource().getId()))
      {
        addFail(new Fail(include, include, FailTyp.INCLUSION, true));
      }
    }
  }

  private void addFail(Fail fail)
  {
    if(!contains(fails, fail))
    {
      fails.add(fail);
    }
  }

  //===========================================================================================================================================================
  //===========================================================================================================================================================
  public P getProductFromList(Integer id)
  {
    for(P product : products)
    {
      if(id.equals(product.getId()))
      {
        return product;
      }
    }

    return null;
  }

  public Include getIncludeFromSetForTargetId(Integer id)
  {
    for(Include include : includes)
    {
      if(id.equals(include.getTarget().getId()))
      {
        return include;
      }
    }

    return null;
  }

  public Exclude getExcludeFromSetForTargetId(Integer id)
  {
    for(Exclude exclude : excludes)
    {
      if(id.equals(exclude.getTarget().getId()))
      {
        return exclude;
      }
    }

    return null;
  }

  private boolean containsTarget(Set<? extends Combination> combinations, Combination combination)
  {
    if(combination == null || combinations == null)
    {
      return false;
    }

    for(Combination c : combinations)
    {
      if(c.getTarget().equals(combination.getTarget()))
      {
        return true;
      }
    }

    return false;
  }

  private boolean containsCombination(Set<? extends Combination> combinations, Combination combination)
  {
    if(combination == null || combinations == null)
    {
      return false;
    }

    for(Combination c : combinations)
    {
      if(c.getTarget().equals(combination.getTarget())
         && c.getSource().equals(combination.getSource()))
      {
        return true;
      }
    }

    return false;
  }

  private boolean contains(Set<Fail> fails, Fail fail)
  {
    if(fails == null || fail == null || fail.getSource() == null || fail.getProblem() == null)
    {
      return false;
    }

    for(Fail f : fails)
    {
      if(fail.getSource().equals(f.getSource()) && fail.getProblem().equals(f.getProblem()))
      {
        return true;
      }
    }

    return false;
  }
}
