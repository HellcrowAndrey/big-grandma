package com.github.mapper;

import com.github.mapper.entityies.Comment;
import com.github.mapper.entityies.Post;
import com.github.mapper.entityies.User;
import com.github.mapper.sql.ReactiveSelect;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootApplication
public class Start implements ApplicationRunner {

    public final ReactiveEntityTemplate template;

    public Start(ReactiveEntityTemplate template) {
        this.template = template;
    }

    public static void main(String[] args) {
        SpringApplication.run(Start.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ReactiveSelect select = this.template.select()
                .from(Post.class)
                .join(User.class, "id", "user_id")
                .join(Comment.class, Post.class, "post_id", "id")
                .toReactiveSelect();
        select.any().subscribe(System.out::println);

        Thread.sleep(5000);

    }

}















/*

Mono<Post> select1 = this.template.select()
                .from(Post.class)
                .join(User.class, "id", "user_id")
                .join(Comment.class, Post.class, "post_id", "id")
                .where(SQLCondition.column(Post.class, "id").eq(1).get())
                .toReactiveSelect()
                .one();


        ReactiveSelect select_zero = this.template.select()
                .from(Post.class)
                .toReactiveSelect();
        select_zero.any()
                .subscribe(System.out::println);

        System.out.println();

        System.out.println("===========================================");
        ReactiveSelect select = this.template.select()
                .from(Post.class)
                .join(User.class, "id", "user_id")
                .join(Comment.class, Post.class, "post_id", "id")
                .toReactiveSelect();
        select.any().subscribe(System.out::println);


        Mono<Post> select1 = this.template.select()
                .from(Post.class)
                .join(User.class, "id", "user_id")
                .join(Comment.class, Post.class, "post_id", "id")
                .where(SQLCondition.column(Post.class, "id").eq(1).get())
                .toReactiveSelect()
                .one();
        select1.subscribe(s -> {
            System.out.println("===========================================");
            System.out.println(s);
        });


 */