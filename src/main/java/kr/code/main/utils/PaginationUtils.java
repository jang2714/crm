package kr.code.main.utils;

public class PaginationUtils {
    private int totalCount; // 전체 데이터 개수
    private int rowsPerPage; // 페이지당 행 개수
    private int currentPage; // 현재 페이지 번호
    private int pageCount; // 전체 페이지 개수

    public PaginationUtils(int totalCount, int rowsPerPage, int currentPage) {
        this.totalCount = totalCount;
        this.rowsPerPage = rowsPerPage;
        this.currentPage = currentPage;
        this.pageCount = getTotalPage(totalCount, rowsPerPage);
    }

    // 전체 페이지 개수를 반환하는 메서드
    private int getTotalPage(int totalCount, int rowsPerPage) {
        int totalPage = totalCount / rowsPerPage;
        if (totalCount % rowsPerPage != 0) {
            totalPage++;
        }
        return totalPage;
    }

    // 페이지당 시작 행 번호를 반환하는 메서드
    public int getStartRow() {
        return (currentPage - 1) * rowsPerPage;
    }

    // 페이지당 끝 행 번호를 반환하는 메서드
    public int getEndRow() {
        return currentPage * rowsPerPage > totalCount ? totalCount : currentPage * rowsPerPage;
    }

    // 현재 페이지 번호가 왼쪽에 더 페이지가 있는지 확인하는 메서드
    public boolean hasPreviousPage() {
        return currentPage > 1;
    }

    // 현재 페이지 번호가 오른쪽에 더 페이지가 있는지 확인하는 메서드
    public boolean hasNextPage() {
        return currentPage < pageCount;
    }

}
