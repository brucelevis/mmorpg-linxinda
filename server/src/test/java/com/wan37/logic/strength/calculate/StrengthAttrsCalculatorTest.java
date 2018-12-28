package com.wan37.logic.strength.calculate;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.wan37.logic.attr.database.PAttrDb;
import com.wan37.logic.attr.database.PlayerAttrDb;
import com.wan37.logic.backpack.database.ItemDb;
import com.wan37.logic.equipment.database.EquipAttrDb;
import com.wan37.logic.equipment.database.EquipDb;
import com.wan37.logic.equipment.database.EquipExtraDb;
import com.wan37.logic.equipment.service.EquipExtraDbGetter;
import com.wan37.logic.player.database.PlayerDb;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StrengthAttrsCalculatorTest {

    @InjectMocks
    StrengthAttrsCalculator _sut;

    @Spy
    EquipExtraDbGetter _getter;

    PlayerDb _db;

    @Before
    public void setUp() {
        _db = mock(PlayerDb.class);
    }

    @Test
    public void calc() {
        // Arrange
        PlayerAttrDb pAttrDb = mock(PlayerAttrDb.class);
        when(_db.getPlayerAttrDb()).thenReturn(pAttrDb);

        when(pAttrDb.getAttrs()).thenReturn(ImmutableMap.of(
                101, mockPAttrDb(101, 10),
                102, mockPAttrDb(102, 20),
                103, mockPAttrDb(103, 30))
        );

        EquipDb equipDb = mock(EquipDb.class);
        when(_db.getEquipDb()).thenReturn(equipDb);

        when(equipDb.getItems()).thenReturn(ImmutableMap.of(
                1, mockItemDb(mockEquipExtraDb(ImmutableList.of(
                        mockEquipAttrDb(101, 10),
                        mockEquipAttrDb(104, 10)))),
                2, mockItemDb(mockEquipExtraDb(ImmutableList.of(
                        mockEquipAttrDb(101, 10),
                        mockEquipAttrDb(102, 20))))
        ));

        // Act
        Map<Integer, Double> result = _sut.calc(_db);

        // Assert
        assertThat(result).isEqualTo(ImmutableMap.of(
                101, 30.0,
                102, 40.0,
                103, 30.0,
                104, 10.0
        ));
    }

    private PAttrDb mockPAttrDb(Integer id, double value) {
        PAttrDb db = new PAttrDb();
        db.setCfgId(id);
        db.setValue(value);
        return db;
    }

    private EquipExtraDb mockEquipExtraDb(List<EquipAttrDb> attrs) {
        EquipExtraDb db = new EquipExtraDb();
        db.setBaseAttrs(attrs);
        return db;
    }

    private EquipAttrDb mockEquipAttrDb(Integer id, double value) {
        EquipAttrDb db = new EquipAttrDb();
        db.setCfgId(id);
        db.setValue(value);
        return db;
    }

    private ItemDb mockItemDb(Object object) {
        ItemDb db = new ItemDb();
        db.setExtraDb(object);
        return db;
    }
}