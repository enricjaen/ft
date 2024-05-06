package j8.futures;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FT {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> result = CompletableFuture.completedFuture(2);
        System.out.println("completed future " + result.get());  // 2
        System.out.println("if thenApply returns a value is internally converted to cf " + result.thenApply(v -> v * 2).get());  // 4
        System.out.println("  so we can consider that thenApply = optional.map.   it could be thought as result.map(v->v*2)");

        System.out.println("if thenApply returns a cf which is internally nested to cf " + result.thenApply(v -> CompletableFuture.completedFuture(v * 2)).get().get());  // 4

        System.out.println("thenCompose returns a new cf " + result.thenCompose(v -> CompletableFuture.completedFuture(v * 2)).get()); //4
        System.out.println("  so thenCompose = optional.flatmap");


        result.thenAccept(v -> System.out.println("thenAccept does not change the cf, only gets v " + v));

        result.thenAccept(v -> System.out.println("thenAccept return a void value"));
        System.out.println("  so thenAccept = consumer");

        result.thenAccept(v -> {
        })
                .thenAccept(v -> System.out.println("thenAccept cannot be chained as gets null " + v));

        result.whenComplete((v, t) -> System.out.println("whenComplete does not change the cf and gets v or t " + v));

        System.out.println("supplyAsync returns a cf and does not receive any v " + result.supplyAsync(() -> 3).get());  // 3
        System.out.println("  so supplyAsync = supplier");


        System.out.println("supplyAsync runs in another thread " + result.supplyAsync(() ->
        {
            System.out.println(Thread.currentThread().getName());
            return 3;
        }).get());  // 3

        result.supplyAsync(() -> 3)
                .whenComplete((v, t) -> System.out.println(Thread.currentThread().getName()));


        result.supplyAsync(() -> 3)
                .thenAccept(v -> System.out.println(Thread.currentThread().getName()));

        result.thenAccept(v ->
                System.out.println(Thread.currentThread().getName())
        );

        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            System.out.println("runAsync runs in another thread and returns void" +Thread.currentThread().getName());
        });

        System.out.println(" runAsync done: "+cf.isDone());
        cf.get();
        System.out.println(" runAsync done: "+cf.isDone());

        System.out.println(" deadlock ");


/*
        CompletableFuture<Integer>[] futures = new CompletableFuture[50];

        IntStream.range(0, 50).forEach(v ->
                futures[v] = result.thenApplyAsync(vv -> {
                    System.out.println("future " + v + " " + Thread.currentThread().getName());
                    try {
                        result.thenApplyAsync(vvv -> {
                            System.out.println("nestedasync " + v + " " + Thread.currentThread().getName());
                            return vvv;
                        }).get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    return vv;
                })
        );

        CompletableFuture.allOf(futures).thenApplyAsync(v -> v).join();

*/

        Executor e = Executors.newFixedThreadPool(1);
        CompletableFuture<Integer> r = result.thenApplyAsync(v->
        {
  /*          CompletableFuture<Integer> r2 = result.thenApplyAsync(vv ->
            {
                return 3;
            },e).thenCompose(j8.futures.FT::processValue);
            return processValue(r2); //works
            //            return processValue(r2.join()); deadlock

*/
            return result.thenApplyAsync(vv ->
            {
                return 3;
            },e).thenCompose(FT::processValue);
        }
        ,e).join();

        System.out.println("end "+r.get());
    }

    private static CompletableFuture<Integer> processValue(CompletableFuture<Integer> cf) {
        // do some processing
        return cf.thenApply(v->4);

    }

        private static CompletableFuture<Integer> processValue(Integer v) {
        // do some processing
        return CompletableFuture.completedFuture(4);
    }

}
