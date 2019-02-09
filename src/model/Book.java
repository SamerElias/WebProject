package model;

public class Book {
	private String name;
	private Double price;
	private String author;
	private String description;
	private String url;
	private String cover;
	private String genre;
	private int featured;
	public Book(String name, Double price, String author, String description, String url, String cover, String genre,
			int featured) {
		super();
		this.name = name;
		this.price = price;
		this.author = author;
		this.description = description;
		this.url = url;
		this.cover = cover;
		this.genre = genre;
		this.featured = featured;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public int getFeatured() {
		return featured;
	}
	public void setFeatured(int featured) {
		this.featured = featured;
	}
	
}
