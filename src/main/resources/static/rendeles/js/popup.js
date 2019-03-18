/* kÃķzÃĐpre igazÃ­tÃĄs */
$.fn.center = function () {
    this.css("position","absolute");
//    this.css("top", (($(window).height() - this.outerHeight()) / 2) + $(window).scrollTop() + "px");
    this.css("top", (($(window).height() - this.outerHeight()) / 2));
    this.css("left", (($(window).width() - this.outerWidth()) / 2) + $(window).scrollLeft() + "px");
    return this;
} 

function showPopup(type) {    
    $.get("popup_" + type + "_panel.php", function(data){
        $('#panel_wrapper').empty().html(data);
        $("#panel_container").css('display','block');                                  
        $("#panel_container").css('visibility','visible');
        $('#panel_wrapper').center();
    });
}

function showCreator(type) {    
    if (type>0){
     $.get("popup_creator_panel.php?pid=" + type, function(data){
        $('#panel_wrapper').empty().html(data);
        $("#panel_container").css('display','block');                                  
        $("#panel_container").css('visibility','visible');
        $('#panel_wrapper').center();
    });
    } else {
     showPopup("creator");
    }
}

function showiPopup(type) {
    $.get("popup_info_panel.php", function(data){
        $('#panel_wrapper').empty().html(data);
        $("#panel_container").css('display','block');
        $("#panel_container").css('visibility','visible');
        $('#panel_wrapper').center();
	$('#infotext').text(type);
    });
}


function hidePopup() {
    $("#panel_container").css('display','none');
    $("#panel_container").css('visibility','hidden');
}
