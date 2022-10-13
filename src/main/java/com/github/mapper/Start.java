package com.github.mapper;

import com.github.mapper.entityies.Comment;
import com.github.mapper.entityies.Post;
import com.github.mapper.entityies.User;
import com.github.mapper.sql.ReactiveSelect;
import com.github.mapper.sql.SQLCondition;
import com.github.mapper.sql.SQLSelect;
import com.github.mapper.sql.SortedType;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class Start implements ApplicationRunner {

    public final DatabaseClient client;

    public Start(DatabaseClient client) {
        this.client = client;
    }

    public static void main(String[] args) {
        SpringApplication.run(Start.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("===========================================");
        ReactiveSelect select = SQLSelect.select(this.client)
                .from(Post.class)
                .join(User.class, "id", "user_id")
                .join(Comment.class, Post.class, "post_id", "id")
                .toReactiveSelect();
        select.any().subscribe(System.out::println);
        Mono<Post> select1 = SQLSelect.select(this.client)
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
        Thread.sleep(10000);
    }

}
