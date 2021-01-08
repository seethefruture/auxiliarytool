function paging() {
    //var currentPage = 1;
    //下一页
    $(document).ready($(function () {
        $("a.pg-next").on("click", function () {
            currentPage++;
            list(currentPage);
        })
    }));
    //上一页
    $(document).ready($(function () {
        $("a.pg-prev").on("click", function () {
            console.log("pre");
            currentPage--;
            //var current = parseInt(currentPage);
            if (currentPage < 1) {
                alert("已经是第一页！");
                currentPage = 1;
            }
            else  list(currentPage);
        })
    }))

    $("a.pg-first").on("click", function () {
        list(1);
    })
}