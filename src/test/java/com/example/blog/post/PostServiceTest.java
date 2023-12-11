package com.example.blog.post;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.blog.adapter.in.command.UpdatePostCommand;
import com.example.blog.domain.Post;
import com.example.blog.application.port.out.CommandPostPort;
import com.example.blog.application.port.out.QueryPostPort;
import com.example.blog.application.service.PostService;
import com.example.blog.mock.FakeCommandPostPort;
import com.example.blog.mock.FakeQueryPostPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PostServiceTest {

    PostService postService;
    CommandPostPort commandPostPort;
    QueryPostPort queryPostPort;

    @BeforeEach
    void init() {
        postService = new PostService(
            commandPostPort = new FakeCommandPostPort(),
            queryPostPort = new FakeQueryPostPort()
        );
    }

    @Test
    @DisplayName("post 생성 성공")
    void create_test() {
        // given
        String subject = "test";
        String content = "test22";
        String writer = "sy.kim@twolinecode.com";

        // when
        Post result = postService.create(subject, content, writer);

        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.subject()).isEqualTo("test");
        assertThat(result.content()).isEqualTo("test22");
        assertThat(result.writer()).isEqualTo("sy.kim@twolinecode.com");
    }

    @Test
    @DisplayName("post 조회 성공")
    void get_test() {
        // given
        // when
        Post result = postService.get(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.subject()).isEqualTo("test");
        assertThat(result.content()).isEqualTo("test22");
        assertThat(result.writer()).isEqualTo("sy.kim@twolinecode.com");
    }

    @Test
    @DisplayName("post 수정 성공")
    void update_test() {
        // given
        Post post = queryPostPort.findById(1L);
        UpdatePostCommand command = new UpdatePostCommand("test11", "test222");

        // when
        Post result = postService.update(post.id(), "sy.kim@twolinecode.com", command);

        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.subject()).isEqualTo("test11");
        assertThat(result.content()).isEqualTo("test222");
        assertThat(result.writer()).isEqualTo("sy.kim@twolinecode.com");
    }
}
