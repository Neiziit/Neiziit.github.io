var lazyLoad = {
  initialize: function() {
    this.eventHandlers();
  },
  eventHandlers: function() {
    $(window).load(lazyLoad.loadImages);
  },
  loadImages: function() {
    var selector = '.fotoAlbum .kat-egorie img[data-src]';

    [].forEach.call(document.querySelectorAll(selector), function(img) {
      img.setAttribute('src', img.getAttribute('data-src'));
      img.onload = function() {
        img.removeAttribute('data-src');
      };
    });
  }
};

var categories = {
  settings: {},
  initialize: function() {
    this.eventHandlers();
  },
  eventHandlers: function() {
    $(document).on('click', '.fotoAlbum .kat-egorie', function() {
      var photos = $(this).next('.fotos').children('div');
      var title  = $(this).find('p').text();
      
      categories.doHideCategories(photos, title);
    });
    $(document).on('click', '.fotoAlbum .backButton', function() {
      $('.fotoAlbum .activeCategory').fadeOut(150, function() {
        $('.fotoAlbum .kat-egorie').fadeIn(150);
      });
    });
    $(document).on('click', '.fotoAlbum .activeCategory a', function(event) {
      event.preventDefault();
      
      categories.doLoadImagebox( $(this) );
    });
    $(document).on('click', '.imageBox', function() {
      $(this).find('.image').fadeOut(150, function() {
        $('.imageBox').remove();
      });
    });
  },
  doHideCategories: function(photos, title) {
    var called = false;
    
    $.each($('.fotoAlbum .kat-egorie'), function() {
      $(this).fadeOut(150, function() {
        if(!called) {
          called = true;

          categories.doMovePhotosToActive(photos, title);
          categories.doLoadImages(photos);

          $('.fotoAlbum .activeCategory').fadeIn(150);
        }
      });
    });
  },
  doMovePhotosToActive: function(photos, title) {
    var activeCategory = $('.fotoAlbum .activeCategory').empty();
    photos.clone().appendTo(activeCategory);
    
    title = $('<div />').addClass('title col-xs-12').text(title);
    
    var backWrapper = $('<div />').addClass('col-sm-4 col-md-3');
    var backButton = $('<div />')
      .addClass('backButton')
      .text('← Terug');
    
    backButton.appendTo(backWrapper);
    backWrapper.prependTo(activeCategory);
    
    title.prependTo(activeCategory);
  },
  doLoadImagebox: function(imageLink) {
    var imagebox, image, img, p;
    console.log(imageLink.attr('title'), imageLink.attr('href'));
    
    imagebox = $('<div />').addClass('imageBox');
    image = $('<div />').addClass('image');
    img = $('<img />').attr({
      src: imageLink.attr('href')
    });
    p = $('<p />').text(imageLink.attr('title'));
    
    p.appendTo( image );
    img.appendTo( image );
    image.appendTo( imagebox );
    imagebox.appendTo( $('body') );
    imagebox.fadeIn(250);
  },
  doLoadImages: function(photos) {
    var selector = '.fotoAlbum .activeCategory img[data-src]';

    [].forEach.call(document.querySelectorAll(selector), function(img) {
      img.setAttribute('src', img.getAttribute('data-src'));
      img.onload = function() {
        img.removeAttribute('data-src');
      };
    });
  }
};

$(function() {
  lazyLoad.initialize();
  categories.initialize();
});