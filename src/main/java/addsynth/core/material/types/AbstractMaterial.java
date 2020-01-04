package addsynth.core.material.types;

import addsynth.core.material.Material;
import addsynth.core.util.StringUtil;

public abstract class AbstractMaterial {

  public final String id_name;
  public final boolean custom;
  public final String name;
  
  public AbstractMaterial(final boolean custom, final String name){
    this.id_name = name;
    this.custom = custom;
    this.name = StringUtil.Capitalize(name);
    Material.list.add(this);
  }

  @Override
  public String toString(){
    return "Material{Type: "+this.getClass().getSimpleName()+", Name: "+name+"}";
  }

}
