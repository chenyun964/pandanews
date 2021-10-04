var gulp = require("gulp")
$ = require("gulp-load-plugins")()
var rename = require("gulp-rename")
var minifyCSS = require("gulp-clean-css")
var sass = require("gulp-sass")
var sourcemaps = require("gulp-sourcemaps")
var autoprefixer = require("autoprefixer")
var postcss = require("gulp-postcss")
// // COMPILE BOOTSTRAP FILES
gulp.task("bootstrap", function() {
    return gulp.src("src/assets/sass/bootstrap/bootstrap.scss")
    .pipe(sourcemaps.init())
    .pipe(sourcemaps.write())
    .pipe(sass.sync().on("error", sass.logError))
    .pipe(sass({
        includePaths: ["src/assets/sass/bootstrap"]
    }))
    .pipe(postcss([autoprefixer()]))
    .pipe(gulp.dest("./dist/assets/css/vendor"))
    .pipe(rename("bootstrap.css"))
    .pipe(minifyCSS())
    .pipe(gulp.dest("./dist/assets/css/vendor"))
    .pipe(gulp.dest("./public/assets/css/vendor"))
})
// // CREATE SASS MAIN BUNDLE FILES
gulp.task("sass", function() {
    return gulp.src("src/assets/sass/common/main.scss")
    .pipe(sourcemaps.init())
    .pipe(sourcemaps.write())
    .pipe(sass.sync().on("error", sass.logError))
    .pipe(sass({
        includePaths: ["sass"]
    }))
    .pipe(postcss([autoprefixer()]))
    .pipe(gulp.dest("./dist/assets/css/common"))
    .pipe(rename("main.bundle.css"))
    .pipe(minifyCSS())
    .pipe(gulp.dest("./dist/assets/css/common"))
    .pipe(gulp.dest("./public/assets/css/common"))
})

// // // CREATE DEMO horizontal BUNDLE FILES
gulp.task("horizontalLayout", function() {
    return gulp.src("src/assets/sass/layouts/vertical/core/*")
    .pipe(sass.sync().on("error", sass.logError))
    .pipe(sass({
        includePaths: ["src/assets/sass/layouts/vertical/core"]
    }))
    .pipe(gulp.dest("./dist/assets/css/layouts/vertical/core"))
    .pipe(minifyCSS())
    .pipe(gulp.dest("./dist/assets/css/layouts/vertical/core"))
    .pipe(gulp.dest("./public/assets/css/layouts/vertical/core"))
})

// // // CREATE THEME FILES FOR horizontal LAYOUT
gulp.task("horizontalThemes", function() {
    return gulp.src("src/assets/sass/layouts/vertical/themes/*")
    .pipe(sass.sync().on("error", sass.logError))
    .pipe(sass({
        includePaths: ["src/assets/sass/layouts/vertical/themes"]
    }))
    .pipe(gulp.dest("./dist/assets/css/layouts/vertical/themes"))
    .pipe(minifyCSS())
    .pipe(gulp.dest("./dist/assets/css/layouts/vertical/themes"))
    .pipe(gulp.dest("./public/assets/css/layouts/vertical/themes"))
})

// // // CREATE BUNDLES FOR horizontal MENU TYPES
gulp.task("horizontalLayoutMenu", function() {
    return gulp.src("src/assets/sass/layouts/vertical/menu-type/*")
    .pipe(sass.sync().on("error", sass.logError))
    .pipe(sass({
        includePaths: ["src/assets/sass/layouts/vertical/menu-type"]
    }))
    .pipe(gulp.dest("./dist/assets/css/layouts/vertical/menu-type"))
    .pipe(minifyCSS())
    .pipe(gulp.dest("./dist/assets/css/layouts/vertical/menu-type"))
    .pipe(gulp.dest("./public/assets/css/layouts/vertical/menu-type"))
})

// // //ADD WATCH
gulp.task("watch", function() {
    gulp.watch("src/**/*.{js,jsx}")
    gulp.watch("src/assets/sass/**/*.scss", gulp.series(["bootstrap","sass","horizontalLayout"]))
})

gulp.task("default", gulp.series(["bootstrap", "sass" , "horizontalThemes" , "horizontalLayoutMenu", "horizontalLayout", 'watch']))
