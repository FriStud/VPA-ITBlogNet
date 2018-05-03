String.prototype.capitalize = function() {
    return this.charAt(0).toUpperCase() + this.slice(1);
};

(function ($) {

    var covers = $('.fadecovers');

    covers.children(':not(:last)').hide();

    var Slider = {

        intervalID: null,
        running: false,
        config: {
            fadeSpeed: 1500,
            delayTime: 3000
        },

        start: function (settings) {
            var slider = this;

            console.log('defaultny config: ', this.config);
            console.log('userove zelanie: ', settings);

            $.extend(this.config, settings);
            console.log('vysledne nastavenie: ', this.config);

            this.intervalID = setInterval(function () {
                covers.children(':last')
                    .fadeOut(slider.config.fadeSpeed, function () { $(this).prependTo(covers) })
                    .prev().fadeIn(slider.config.fadeSpeed);
            }, slider.config.delayTime);

            this.running = true;
            console.log('zacali sme');
        },

        pause: function () {
            clearInterval(this.intervalID);
            this.intervalID = null;

            this.running = false;
            console.log('zapauzovane');
        },

        resume: function () {
            if (!this.intervalID) this.start();
        },

        toggle: function () {
            if (this.running) this.pause();
            else this.resume();
        }

    }

    Slider.start();

    covers.on('click', function () {
        Slider.toggle();
    });

    var overlay = $('<div>', { id: 'overlay' });
    overlay.appendTo('body').hide();

    overlay.on('click', function () {
        $(this).fadeOut('fast');
    });



    $(document).on('keyup', function (event) {
        if (event.which === 27) overlay.fadeOut('fast');
    });

    var list = $('.jokes');

    list.find('dd').slideUp();

    list.find('dt').on('click', function (event) {
        var dt = $(this),
            dd = dt.next();

        dd.slideToggle()
            .siblings('dd').slideUp();
    });

    //SCROLOVANIE

    var scr = $('.navbar-menu'),
        menuLinks = scr.find('a');

    menuLinks.on('click', function (event) {

        $('html,body').animate({scrollTop: $(this.hash).offset().top})
        event.preventDefault();


    });

    var backToTop = $('<a>', {
        href: '#home',
        class: 'back-to-top',
        html: '<i class="fa fa-caret-up fa-5x"></i>'
    });

    backToTop
        .hide()
        .appendTo('body')
        .on('click', function () {
            $('html,body').animate({ scrollTop: 0 });
        });


    var win = $(window);
    win.scroll(function () {
        if (win.scrollTop() >= 500) backToTop.fadeIn();
        else backToTop.hide();
    });

})(jQuery)