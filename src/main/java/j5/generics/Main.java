package j5.generics;

public class Main {
    class A<T>{
        T t;

        @Override
        public String toString() {
            return t.getClass().getName();
        }
    }
    class B<T> extends A<T>{

    }
    class C extends B<Long> {

    }
    public static void main(String[] args) {
        C c = new Main().new C();
        c.t = new Long(12);
        System.out.println(c);
    }
}
