package com.wan37.logic.props.resource.add;

import com.wan37.logic.backpack.database.BackpackDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.backpack.service.find.BackpackEmptyIndexFinder;
import com.wan37.logic.props.config.PropsCfg;
import com.wan37.logic.props.config.PropsCfgLoader;
import com.wan37.logic.props.init.PropsExtraInitializer;
import com.wan37.logic.props.resource.ResourceElement;
import com.wan37.util.IdTool;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResourceItemAdderTest {

    /**
     * 创建一个实例，其余用@Mock（或@Spy）注解创建的mock将被注入到用该实例中。
     */
    @InjectMocks
    ResourceItemAdder _sut;

    @Mock
    PropsCfgLoader _loader;

    @Mock
    IdTool _idTool;

    @Spy
    BackpackEmptyIndexFinder _finder;

    @Mock
    PropsExtraInitializer _initializer;

    ResourceElement _element;
    BackpackDb _db;
    PropsCfg _cfg;

    @Before
    public void setUp() {
        when(_initializer.init(any())).thenReturn(null);

        _element = mock(ResourceElement.class);

        _db = new BackpackDb();
        _db.setIndexs(new HashSet<>());
        _db.setItemMap(new HashMap<>());

        _cfg = mock(PropsCfg.class);
        when(_loader.load(anyInt())).thenReturn(Optional.of(_cfg));
    }

    @Test
    public void add_加入可叠加物品() {
        // Arrange
        _db.setCapacity(20);
        Integer cfgId = 101;

        when(_element.getCfgId()).thenReturn(cfgId);
        when(_element.getAmount()).thenReturn(99L);

        when(_cfg.getId()).thenReturn(cfgId);
        when(_cfg.getMaxOverLay()).thenReturn(40);

        // Act
        add();

        // Assert
        assertThat(result()).isEqualTo(new Object[][]{
                {1, cfgId, 40},
                {2, cfgId, 40},
                {3, cfgId, 19},
        });
    }

    @Test
    public void add_加入不可叠加物品() {
        // Arrange
        _db.setCapacity(20);
        Integer cfgId = 101;

        when(_element.getCfgId()).thenReturn(cfgId);
        when(_element.getAmount()).thenReturn(10L);

        when(_cfg.getId()).thenReturn(cfgId);
        when(_cfg.getMaxOverLay()).thenReturn(1);

        // Act
        add();

        // Assert
        assertThat(_db.getItemMap()).isNotEmpty();
    }

    @Test
    public void add_在已存在可叠加物品的背包中加入可叠加物品() {
        // Arrange
        _db.setCapacity(20);
        Integer cfgId = 101;

        Map<Integer, ItemDb> itemMap = new HashMap<>();
        itemMap.put(2, mockItemDb(2, cfgId, 30));
        itemMap.put(3, mockItemDb(3, cfgId, 20));
        _db.setItemMap(itemMap);

        when(_element.getCfgId()).thenReturn(cfgId);
        when(_element.getAmount()).thenReturn(100L);

        when(_cfg.getId()).thenReturn(cfgId);
        when(_cfg.getMaxOverLay()).thenReturn(40);

        // Act
        add();

        // Assert
        assertThat(result()).isEqualTo(new Object[][]{
                {1, cfgId, 40},
                {2, cfgId, 40},
                {3, cfgId, 40},
                {4, cfgId, 30},
        });
    }

    void add() {
        _sut.add(_element, _db);
    }

    ItemDb mockItemDb(Integer index, Integer cfgId, int amount) {
        ItemDb db = new ItemDb();
        db.setIndex(index);
        db.setCfgId(cfgId);
        db.setAmount(amount);
        return db;
    }

    Object[] result() {
        return _db.getItemMap().values().stream()
                .sorted(Comparator.comparingInt(ItemDb::getIndex))
                .map(i -> new Object[]{i.getIndex(), i.getCfgId(), i.getAmount()})
                .toArray();
    }
}