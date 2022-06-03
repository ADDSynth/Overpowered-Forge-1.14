package addsynth.core.game.items;

public enum ArmorMaterial {

  LEATHER("leather"),
  CHAINMAIL("chainmail"),
  IRON("iron"),
  GOLD("gold"),
  DIAMOND("diamond");

  public final String name;

  private ArmorMaterial(final String name){
    this.name = name;
  }

}
