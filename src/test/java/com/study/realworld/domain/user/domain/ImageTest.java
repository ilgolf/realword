package com.study.realworld.domain.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ImageTest {

    @DisplayName("Image 인스턴스 기본 생성자 테스트")
    @Test
    void default_constructor_test() {
        final Image image = new Image();

        assertAll(
                () -> assertThat(image).isNotNull(),
                () -> assertThat(image).isExactlyInstanceOf(Image.class)
        );
    }

    @DisplayName("Image 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final String imagePath = "iamgePath";
        final Image image = new Image(imagePath);

        assertAll(
                () -> assertThat(image).isNotNull(),
                () -> assertThat(image).isExactlyInstanceOf(Image.class)
        );
    }

    @DisplayName("Image 인스턴스 getter 기능 테스트")
    @Test
    void getter_test() {
        final String imagePath = "iamgePath";
        final Image image = new Image(imagePath);

        assertThat(image.path()).isEqualTo(imagePath);
    }

}