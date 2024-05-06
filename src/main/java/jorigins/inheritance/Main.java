package jorigins.inheritance;

public class Main {

    public static void main(String[] args) {

            Main main = new Main();
            main.bar();
    }

    @FunctionalInterface
    public interface AnyAnimalCare<T> {
        public void interactsWith(T anAnimal);
    }

    public class AnyAnimal {
    }

    class Tiger extends AnyAnimal {
        int a;
    }

    class Piolin extends AnyAnimal {
        int p;
    }
    public void bar() {
        AnyAnimalCare anyAnimalCare = null;

        AnyAnimal anyAnimal =null;
        Tiger tiger = new Tiger();
        Piolin piolin = new Piolin();

//        interact((AnyAnimal bc)->{} ,tiger);

//        anyAnimalCare = (AnyAnimal bc)->{};
//        anyAnimalCare = this::animalCare;
//        anyAnimalCare = this::feedTiger;

        AnyAnimalCare<Tiger> tigerCare = this::feedTiger;
        interact(tigerCare,tiger);
      //  interact(tigerCare,piolin);

//        interact(anyAnimalCare,tiger);
//        interact(anyAnimalCare,piolin);

        //feedTiger(tiger);

//        feedTiger(anyAnimal);
    }

    public void animalCare(AnyAnimal bc) {
    }

    public void feedTiger(Tiger tiger) {
        System.out.println("feeding tiger");
    }

    public <T> void interact(AnyAnimalCare<T> anyAnimalCare, T anyAnimal) {
        anyAnimalCare.interactsWith(anyAnimal);
    }
}
