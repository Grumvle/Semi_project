  
  
  
$(function () {
	
  
    $('.starRev span').click(function(){
    $(this).parent().children('span').removeClass('on');
    $(this).addClass('on').prevAll('span').addClass('on');
    return false;
  });

  $("#id").on("click", function(e){
	  
    let main_idx = $(this).parents('.modal-dialog').find("#detail_idx").val();
    let star = $("#starRev .on"+main_idx).length;
    let review = $("#reviewContents"+main_idx).val();
    
	console.log("Ajax호출-Star");	
	console.log("idx 값 : "+main_idx);	
  
	$.ajax({
      type: "post",
      url: "star_review.do",
      dataType: "json",
      data: {
    	  star : star,
    	  review : review,
    	  idx : main_idx
      },
      success: function(data){
        
      }
    });
  });
  
  });