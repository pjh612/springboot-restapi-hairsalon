package com.kdt.hairsalon.repository.menu;

import com.kdt.hairsalon.model.Menu;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class MenuJdbcRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;

    private static EmbeddedMysql embeddedMySql;

    @BeforeAll
    void setup() {
        MysqldConfig config = aMysqldConfig(Version.v5_7_latest)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMySql = anEmbeddedMysql(config)
                .addSchema("test-hair_salon", classPathScript("schema.sql"))
                .start();

    }

    @AfterAll
    static void cleanUp() {
        embeddedMySql.stop();
    }

    @Test
    @DisplayName("시술 메뉴를 추가할 수 있다.")
    void insertTest() {
        //given
        Menu menu = new Menu(UUID.randomUUID(), "menuA", 1000, LocalDateTime.now(), LocalDateTime.now());

        //when
        menuRepository.insert(menu);
        Optional<Menu> foundMenu = menuRepository.findById(menu.getId());

        //then
        assertThat(foundMenu.isPresent(), is(true));
        assertThat(foundMenu.get(), samePropertyValuesAs(menu));
    }

    @Test
    @DisplayName("Menu 삭제 테스트")
    void deleteByIdTest() {
        //given
        Menu menu = new Menu(UUID.randomUUID(), "menuB", 1000, LocalDateTime.now(), LocalDateTime.now());
        menuRepository.insert(menu);

        //when
        menuRepository.deleteById(menu.getId());
        Optional<Menu> foundMenu = menuRepository.findById(menu.getId());

        //then
        assertThat(foundMenu.isEmpty(), is(true));
    }
}