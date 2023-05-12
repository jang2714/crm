package kr.code.main.meeting.vo;

public class PagingVO {

    //전체페이지 수
    private int totalPage;
    private int nowPageNumber;
    private int blockPerPage;
    private int pagePerRows;
    private int  totalRows;
    private int nowBlock;
    private int totalBlock;

    public PagingVO(int nowPageNumber, int totalRows){
        //로직에서는 페이지번호 시작을 0 부터로 처리
        this.nowPageNumber = nowPageNumber - 1;
        this.totalRows = totalRows;
        this.pagePerRows = 3;
        this.blockPerPage = 10;
    }

    //시작위치 가져오기 - SQL 용
    public int getBeginPage(){return nowPageNumber * pagePerRows;}

    //마지막 위치 가져오기
    public int getEndPage(){return pagePerRows;}

    public int getTotalPage(){
        double result = Math.ceil( (double)totalRows / pagePerRows);
        return (int)result;
    }

    //현재 블럭 위치 - 첫번째 : 0부터
    public int getNowBlock(){
        double retVal = ((double)this.nowPageNumber) / this.blockPerPage;
        //현재 블럭 = 현재 페이지 / 블럭 당 페이지 수
        return (int)retVal;
    }

    //전체 블럭 개수
    public int getTotalBlock(int totalPage){
        double retVal = Math.ceil((double)totalPage / this.blockPerPage);
        System.out.println("토탈 블럭 : " + retVal);
        return (int)retVal;
    }

    public String pageHTML(){
        this.totalPage = this.getTotalPage();
        this.nowBlock = this.getNowBlock();
        this.totalBlock = this.getTotalBlock(this.totalPage);
        int pageNumber = 0;
        //html을 담을 빌더
        StringBuilder sb = new StringBuilder();

        if(nowPageNumber > 0){
            pageNumber = 0;
            sb.append("li class='page-item'><a class='page-link' href='javascript:void(0);' onclick='goPage(" + pageNumber + ");'>");
            sb.append("처음</a></li>");
        }

        //만약 블럭이 넘어간게 있을 경우
        if(nowBlock > 0){
            pageNumber = (nowBlock - 1) * blockPerPage;
            sb.append("<li class='page-item'>");
            sb.append("<a class='page-link' href='javascript:void(0);' onclick='goPage(" + pageNumber +");'>");
            sb.append("이전</a>");
            sb.append("</li>");
        }

        //페이지 그리기
        String isActive="";
        for(int i = 0; i < blockPerPage; i++){
            pageNumber = (nowBlock * blockPerPage) + i;
            isActive = "";

            if(pageNumber == nowPageNumber){
                isActive="active";
            }

            sb.append("<li class='page-item" + isActive + "'>");
            sb.append("<a class='page-link' href='javascript:void(0);' onclick='goPage(" + pageNumber + ");'>");
            sb.append((pageNumber + 1) + "</a>");
            sb.append("</li>");

            if( (pageNumber + 1) == totalPage){
                break;
            }
        }

        //다음 블럭으로 가기 버튼 만들기
        if((nowBlock + 1) < totalBlock){
            pageNumber = (nowBlock + 1) * blockPerPage;
            sb.append("<li class='page-item'>");
            sb.append(" <a class='page-link' href=\"javascript:void(0);\" onclick=\"goPage(" + pageNumber + ");\">");
            sb.append("다음</a></li>");
        }

        //마지막 페이지로 이동 버튼 만들기
        if( (nowPageNumber + 1) < totalPage){
            sb.append("<li class='page-item'>");
            sb.append(" <a class='page-link' href=\"javascript:void(0);\"  onclick=\"goPage(" + (totalPage -1) + ");\" >");
            sb.append("마지막</a></li>");
        }

        return sb.toString();
    }

}
