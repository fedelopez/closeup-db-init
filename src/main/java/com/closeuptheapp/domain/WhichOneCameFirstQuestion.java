package com.closeuptheapp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;

/**
 * @author Federico
 */
@SuppressWarnings({"JpaDataSourceORMInspection"})
@Entity(name = "which_one_came_first")
public class WhichOneCameFirstQuestion extends AbstractQuestion {

    private String movieTitle1;
    private String movieTitle2;

    private String movieTitle1Image;
    private String movieTitle2Image;

    private int movieTitle1Year;
    private int movieTitle2Year;

    @Transient
    protected Type getType() {
        return Type.WHICH_ONE_CAME_FIRST;
    }

    @Transient
    public void setHints(List<String> values) {
        setMovieTitle1(values.get(0));
        setMovieTitle2(values.get(1));
        String hint1 = values.get(2);
        String hint2 = values.get(3);
        setMovieTitle1Image(extractMovieTitle(hint1) + ".jpg");
        setMovieTitle2Image(extractMovieTitle(hint2) + ".jpg");
        setMovieTitle1Year(extractYear(hint1));
        setMovieTitle2Year(extractYear(hint2));
    }

    @Column(name = "movie_title1")
    public String getMovieTitle1() {
        return movieTitle1;
    }

    public void setMovieTitle1(String movieTitle1) {
        this.movieTitle1 = movieTitle1;
    }

    @Column(name = "movie_title2")
    public String getMovieTitle2() {
        return movieTitle2;
    }

    public void setMovieTitle2(String movieTitle2) {
        this.movieTitle2 = movieTitle2;
    }

    @Column(name = "movie_title1_image")
    public String getMovieTitle1Image() {
        return movieTitle1Image;
    }

    public void setMovieTitle1Image(String movieTitle1Image) {
        this.movieTitle1Image = movieTitle1Image;
    }

    @Column(name = "movie_title2_image")
    public String getMovieTitle2Image() {
        return movieTitle2Image;
    }

    public void setMovieTitle2Image(String movieTitle2Image) {
        this.movieTitle2Image = movieTitle2Image;
    }

    @Column(name = "movie_title1_year")
    public int getMovieTitle1Year() {
        return movieTitle1Year;
    }

    public void setMovieTitle1Year(int movieTitle1Year) {
        this.movieTitle1Year = movieTitle1Year;
    }

    @Column(name = "movie_title2_year")
    public int getMovieTitle2Year() {
        return movieTitle2Year;
    }

    public void setMovieTitle2Year(int movieTitle2Year) {
        this.movieTitle2Year = movieTitle2Year;
    }

    private String extractMovieTitle(String hint) {
        return hint.substring(0, hint.indexOf(","));
    }

    private int extractYear(String hint) {
        return Integer.parseInt(hint.substring(hint.indexOf(",") + 1, hint.length()).trim());
    }

}