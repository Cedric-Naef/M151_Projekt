package ch.bbzw.m151.swshop;

import ch.bbzw.m151.swshop.model.Merch;
import ch.bbzw.m151.swshop.model.MerchType;
import ch.bbzw.m151.swshop.model.Price;
import ch.bbzw.m151.swshop.repo.MerchRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations="classpath:test.properties")
public class MerchRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MerchRepo merchRepo;

    private Price samplePrice;

    @BeforeEach
    void setUp() {
        samplePrice = new Price(5.0f, 0);
        entityManager.persistAndFlush(samplePrice);
    }

    @Test
    public void testFindById() {
        Merch merch = new Merch("Lichtschwert", MerchType.WAFFE, samplePrice);
        entityManager.persistAndFlush(merch);

        Merch found = merchRepo.findById(merch.getId());

        assertThat(found.getId()).isEqualTo(merch.getId());
    }

    @Test
    public void testFindByMerchName() {
        Merch merch = new Merch("Robe", MerchType.KLEIDUNG, samplePrice);
        entityManager.persistAndFlush(merch);

        Merch found = MerchRepo.findByMerchName("Robe");

        assertThat(found.getMerchName()).isEqualTo(merch.getMerchName());
    }

    @Test
    public void testFindByMerchType() {
        Merch merch = new Merch("Holocrom", FruitType.PROP, samplePrice);
        entityManager.persistAndFlush(merch);

        List<Merch> found = merchRepo.findByMerchType(FruitType.Prop);

        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0)).isEqualTo(merch);
    }
}
