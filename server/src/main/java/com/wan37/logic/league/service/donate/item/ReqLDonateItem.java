package com.wan37.logic.league.service.donate.item;

import java.util.List;

public interface ReqLDonateItem {

    interface Factory {

        ReqLDonateItem create(String args);
    }

    List<LDonateItem> getDonateItems();
}
