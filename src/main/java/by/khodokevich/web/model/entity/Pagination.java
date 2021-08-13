package by.khodokevich.web.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Pagination {
    private int currentPage;
    private int onePageNumberItems;
    private int numberItems;
    private int numberVisiblePage = 5;

    public Pagination(int currentPage, int onePageNumberItems, int numberItems) {
        this.currentPage = currentPage;
        this.onePageNumberItems = onePageNumberItems;
        this.numberItems = numberItems;
    }

    public Pagination(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getOnePageNumberItems() {
        return onePageNumberItems;
    }

    public void setOnePageNumberItems(int onePageNumberItems) {
        this.onePageNumberItems = onePageNumberItems;
    }

    public int getNumberItems() {
        return numberItems;
    }

    public void setNumberItems(int numberItems) {
        this.numberItems = numberItems;
    }

    public int getLastIndexBeforeFirstItemOnPage() {
        return onePageNumberItems * (currentPage - 1);
    }

    public int getNumberVisiblePage() {
        return numberVisiblePage;
    }

    public void setNumberVisiblePage(int numberVisiblePage) {
        this.numberVisiblePage = numberVisiblePage;
    }

    public List<Integer> getListVisiblePage() {
        List<Integer> numberPageList = new ArrayList<>();
        int leftVisible = numberVisiblePage / 2;
        int rightVisible = numberVisiblePage / 2;
        for (int i = -leftVisible; i <= rightVisible; i++) {
            int indexPage = currentPage + i;
            int lastIndex = getLastPage();
            if (indexPage > 1 && indexPage < lastIndex) {
                numberPageList.add(indexPage);
            }
        }
        return numberPageList;
    }

    public boolean showLeftDivider() {
        return currentPage > (numberVisiblePage - 1) / 2 + 1;
    }

    public boolean showRightDivider() {
        int lastPage = getLastPage();
        return currentPage < lastPage - (numberVisiblePage - 1) / 2;
    }


    public int getLastPage() {
        int lastPage;
        if (numberItems >= onePageNumberItems) {
            lastPage = (int) Math.ceil(numberItems / (double) onePageNumberItems);
        } else {
            lastPage = 1;
        }
        return lastPage;
    }
}
