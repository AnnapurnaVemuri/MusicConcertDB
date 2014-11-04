import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import oracle.jdbc.pool.OracleDataSource;

public class DatabaseHelper {
	private Connection connection;
	private static final String DB_URL = "jdbc:oracle:thin:@//w4111j.cs.columbia.edu:1521/ADB";
	private static final String DB_USERNAME = "amv2164";
	private static final String DB_PASSWORD = "vidhibadha9";
	
	public DatabaseHelper() {
		OracleDataSource ods;
		try {
			ods = new oracle.jdbc.pool.OracleDataSource();
	        ods.setURL(DB_URL);
	        ods.setUser(DB_USERNAME);
	        ods.setPassword(DB_PASSWORD);
	        connection = ods.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkLogin (String username, String password) {
		String loginQuery = "select * from member where name = ? and password = ?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.prepareStatement(loginQuery);
			stmt.setString(1, username);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public void close() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public int getMemberId(String username) {
		String memberidQuery = "select member_id from member where name=?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.prepareStatement(memberidQuery);
			stmt.setString(1, username);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("member_id");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return -1;
	}

	public List<Concert> getLikedConcerts(int memberid) {
		List<Concert> concertList = new ArrayList<Concert>();
		String concertQuery = "select concert_id from likes_concert where member_id = ?";
		String concertDetails = "select * from concert where concert_id = ?";
		PreparedStatement stmt = null, detailsStmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.prepareStatement(concertQuery);
			detailsStmt = connection.prepareStatement(concertDetails);
			stmt.setInt(1, memberid);
			rs = stmt.executeQuery();
			List<Integer> concertIdList = new ArrayList<Integer>();
			while (rs.next()) {
				concertIdList.add(rs.getInt("concert_id"));
			}
			for (Integer concertId : concertIdList) {
				detailsStmt.setInt(1, concertId);
				rs = detailsStmt.executeQuery();
				if (rs.next()) {
					Concert concert = new Concert(rs.getInt("concert_id"), rs.getString("name"), rs.getString("date_of_concert"), rs.getString("venue"), rs.getString("url"));
					concertList.add(concert);
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (detailsStmt != null) {
					detailsStmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return concertList;
	}

	public List<Band> getLikedBands(int memberid) {
		List<Band> bandList = new ArrayList<Band>();
		String bandQuery = "select band_id from likes_band where member_id = ?";
		String bandDetails = "select * from band where band_id = ?";
		PreparedStatement stmt = null, detailsStmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.prepareStatement(bandQuery);
			detailsStmt = connection.prepareStatement(bandDetails);
			stmt.setInt(1, memberid);
			rs = stmt.executeQuery();
			List<Integer> bandIdList = new ArrayList<Integer>();
			while (rs.next()) {
				bandIdList.add(rs.getInt("band_id"));
			}
			for (Integer bandId : bandIdList) {
				detailsStmt.setInt(1, bandId);
				rs = detailsStmt.executeQuery();
				if (rs.next()) {
					Band band = new Band(rs.getInt("band_id"), rs.getString("name"), rs.getString("webpage"));
					bandList.add(band);
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (detailsStmt != null) {
					detailsStmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return bandList;
	}

	public List<Album> getLikedAlbums(int memberid) {
		List<Album> albumList = new ArrayList<Album>();
		String albumQuery = "select album_id from likes_album where member_id = ?";
		String albumDetails = "select * from album where album_id = ?";
		PreparedStatement stmt = null, detailsStmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.prepareStatement(albumQuery);
			detailsStmt = connection.prepareStatement(albumDetails);
			stmt.setInt(1, memberid);
			rs = stmt.executeQuery();
			List<Integer> albumIdList = new ArrayList<Integer>();
			while (rs.next()) {
				albumIdList.add(rs.getInt("album_id"));
			}
			for (Integer albumId : albumIdList) {
				detailsStmt.setInt(1, albumId);
				rs = detailsStmt.executeQuery();
				if (rs.next()) {
					Album album = new Album(rs.getInt("album_id"), rs.getString("name"));
					albumList.add(album);
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (detailsStmt != null) {
					detailsStmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return albumList;
	}

	public List<Artist> getLikedArtists(int memberid) {
		List<Artist> artistList = new ArrayList<Artist>();
		String artistQuery = "select artist_id from likes_artist where member_id = ?";
		String artistDetails = "select * from artist where artist_id = ?";
		PreparedStatement stmt = null, detailsStmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.prepareStatement(artistQuery);
			detailsStmt = connection.prepareStatement(artistDetails);
			stmt.setInt(1, memberid);
			rs = stmt.executeQuery();
			List<Integer> artistIdList = new ArrayList<Integer>();
			while (rs.next()) {
				artistIdList.add(rs.getInt("artist_id"));
			}
			for (Integer artistId : artistIdList) {
				detailsStmt.setInt(1, artistId);
				rs = detailsStmt.executeQuery();
				if (rs.next()) {
					Artist artist = new Artist(rs.getInt("artist_id"), rs.getString("name"), rs.getString("webpage"));
					artistList.add(artist);
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (detailsStmt != null) {
					detailsStmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return artistList;
	}

	public List<Song> getLikedSongs(int memberid) {
		List<Song> songList = new ArrayList<Song>();
		String songQuery = "select song_id from likes_song where member_id = ?";
		String songDetails = "select * from song where song_id = ?";
		PreparedStatement stmt = null, detailsStmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.prepareStatement(songQuery);
			detailsStmt = connection.prepareStatement(songDetails);
			stmt.setInt(1, memberid);
			rs = stmt.executeQuery();
			List<Integer> songIdList = new ArrayList<Integer>();
			while (rs.next()) {
				songIdList.add(rs.getInt("song_id"));
			}
			for (Integer songId : songIdList) {
				detailsStmt.setInt(1, songId);
				rs = detailsStmt.executeQuery();
				if (rs.next()) {
					Song song = new Song(rs.getInt("song_id"), rs.getString("name"), rs.getString("video_link"));
					songList.add(song);
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (detailsStmt != null) {
					detailsStmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return songList;
	}

	public List<Genre> getLikedGenre(int memberid) {
		List<Genre> genreList = new ArrayList<Genre>();
		String genreQuery = "select genre_id from likes_genre where member_id = ?";
		String genreDetails = "select * from genre where genre_id = ?";
		PreparedStatement stmt = null, detailsStmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.prepareStatement(genreQuery);
			detailsStmt = connection.prepareStatement(genreDetails);
			stmt.setInt(1, memberid);
			rs = stmt.executeQuery();
			List<Integer> genreIdList = new ArrayList<Integer>();
			while (rs.next()) {
				genreIdList.add(rs.getInt("genre_id"));
			}
			for (Integer genreId : genreIdList) {
				detailsStmt.setInt(1, genreId);
				rs = detailsStmt.executeQuery();
				if (rs.next()) {
					Genre genre = new Genre(rs.getInt("genre_id"), rs.getString("name"));
					genreList.add(genre);
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (detailsStmt != null) {
					detailsStmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return genreList;
	}

	public List<Band> getBandsPerformingInConcert(int concertId) {
		List<Band> bandList = new ArrayList<Band>();
		String bandQuery = "select * from band_concert where concert_id = ?";
		String bandDetails = "select * from band where band_id = ?";
		PreparedStatement stmt = null, detailsStmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.prepareStatement(bandQuery);
			detailsStmt = connection.prepareStatement(bandDetails);
			stmt.setInt(1, concertId);
			rs = stmt.executeQuery();
			List<Integer> bandIdList = new ArrayList<Integer>();
			while (rs.next()) {
				bandIdList.add(rs.getInt("band_id"));
			}
			for (Integer bandId : bandIdList) {
				detailsStmt.setInt(1, bandId);
				rs = detailsStmt.executeQuery();
				if (rs.next()) {
					Band band = new Band(rs.getInt("band_id"), rs.getString("name"), rs.getString("webpage"));
					bandList.add(band);
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (detailsStmt != null) {
					detailsStmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return bandList;
	}

	public List<Artist> getArtistsPerformingInConcert(int concertId) {
		List<Artist> artistList = new ArrayList<Artist>();
		String artistQuery = "select * from artist_concert where concert_id = ?";
		String artistDetails = "select * from artist where artist_id = ?";
		PreparedStatement stmt = null, detailsStmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.prepareStatement(artistQuery);
			detailsStmt = connection.prepareStatement(artistDetails);
			stmt.setInt(1, concertId);
			rs = stmt.executeQuery();
			List<Integer> artistIdList = new ArrayList<Integer>();
			while (rs.next()) {
				artistIdList.add(rs.getInt("artist_id"));
			}
			for (Integer artistId : artistIdList) {
				detailsStmt.setInt(1, artistId);
				rs = detailsStmt.executeQuery();
				if (rs.next()) {
					Artist artist = new Artist(rs.getInt("artist_id"), rs.getString("name"), rs.getString("webpage"));
					artistList.add(artist);
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (detailsStmt != null) {
					detailsStmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return artistList;
	}

	public Set<Genre> getGenreOfBands(List<Band> bandsPerforming) {
		Set<Genre> genreSet = new HashSet<Genre>();
		for (Band band : bandsPerforming) {
			String q1 = "select album_id from artist_album where band_id = ?";
			String q2 = "select song_id from song_artist where band_id = ?";
			PreparedStatement s1 = null,s2 = null;
			ResultSet rs = null;
			try {
				s1 = connection.prepareStatement(q1);
				s2 = connection.prepareStatement(q2);
				s1.setInt(1, band.getId());
				rs = s1.executeQuery();
				while (rs.next()) {
					genreSet.add(getGenreOfAlbum(rs.getInt("album_id")));
				}
				s2.setInt(1, band.getId());
				rs = s2.executeQuery();
				while (rs.next()) {
					genreSet.add(getGenreOfAlbum(rs.getInt("song_id")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (s1 != null) {
						s1.close();
					}
					if (s2 != null) {
						s2.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return genreSet;
	}
	
	public Set<Genre> getGenreOfArtists(List<Artist> artistsPerforming) {
		Set<Genre> genreSet = new HashSet<Genre>();
		for (Artist artist : artistsPerforming) {
			String q1 = "select album_id from artist_album where artist_id = ?";
			String q2 = "select song_id from song_artist where artist_id = ?";
			PreparedStatement s1 = null,s2 = null;
			ResultSet rs = null;
			try {
				s1 = connection.prepareStatement(q1);
				s2 = connection.prepareStatement(q2);
				s1.setInt(1, artist.getId());
				rs = s1.executeQuery();
				while (rs.next()) {
					genreSet.add(getGenreOfAlbum(rs.getInt("album_id")));
				}
				s2.setInt(1, artist.getId());
				rs = s2.executeQuery();
				while (rs.next()) {
					genreSet.add(getGenreOfAlbum(rs.getInt("song_id")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (s1 != null) {
						s1.close();
					}
					if (s2 != null) {
						s2.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return genreSet;
	}

	public Genre getGenreOfAlbum(int id) {
		String q1 = "select genre_id from album_genre where album_id = ?";
		PreparedStatement s1 = null;
		ResultSet rs = null;
		int genreid;
		try {
			s1 = connection.prepareStatement(q1);
			s1.setInt(1, id);
			rs = s1.executeQuery();
			if (rs.next()) {
				genreid = rs.getInt("genre_id");
				String genrename = getGenreName(genreid);
				if (genrename != null) {
					return new Genre(id, genrename);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (s1 != null) {
					s1.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public Genre getGenreOfSong(int id) {
		String q1 = "select genre_id from song_genre where song_id = ?";
		PreparedStatement s1 = null;
		ResultSet rs = null;
		int genreid;
		try {
			s1 = connection.prepareStatement(q1);
			s1.setInt(1, id);
			rs = s1.executeQuery();
			if (rs.next()) {
				genreid = rs.getInt("genre_id");
				String genrename = getGenreName(genreid);
				if (genrename != null) {
					return new Genre(id, genrename);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (s1 != null) {
					s1.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public String getGenreName(int id) {
		String q1 = "select name from genre where genre_id = ?";
		PreparedStatement s1 = null;
		ResultSet rs = null;
		try {
			s1 = connection.prepareStatement(q1);
			s1.setInt(1, id);
			rs = s1.executeQuery();
			if (rs.next()) {
				return rs.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (s1 != null) {
					s1.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Band getBandOfAlbum(int id) {
		String q1 = "select band_id from band_album where album_id = ?";
		String q2 = "select * from band where band_id = ?";
		PreparedStatement s1 = null, s2 = null;
		ResultSet rs = null, rs2 = null;
		try {
			s1 = connection.prepareStatement(q1);
			s2 = connection.prepareStatement(q2);
			s1.setInt(1, id);
			rs = s1.executeQuery();
			if (rs.next()) {
				s2.setInt(1, rs.getInt("band_id"));
				rs2 = s2.executeQuery();
				if (rs2.next()) {
					return new Band(rs.getInt("band_id"), rs2.getString("name"), rs2.getString("webpage"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (s1 != null) {
					s1.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Artist getArtistOfAlbum(int id) {
		String q1 = "select artist_id from artist_album where album_id = ?";
		String q2 = "select * from artist where artist_id = ?";
		PreparedStatement s1 = null, s2 = null;
		ResultSet rs = null, rs2 = null;
		try {
			s1 = connection.prepareStatement(q1);
			s2 = connection.prepareStatement(q2);
			s1.setInt(1, id);
			rs = s1.executeQuery();
			if (rs.next()) {
				s2.setInt(1, rs.getInt("artist_id"));
				rs2 = s2.executeQuery();
				if (rs2.next()) {
					return new Artist(rs.getInt("artist_id"), rs2.getString("name"), rs2.getString("webpage"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (rs2 != null) {
					rs2.close();
				}
				if (s1 != null) {
					s1.close();
				}
				if (s2 != null) {
					s2.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public List<Song> getSongsInAlbum(int id) {
		String q1 = "select song_id from song_album where album_id = ?";
		String q2 = "select * from song where song_id = ?";
		List<Song> songList = new ArrayList<Song>();
		PreparedStatement s1 = null, s2 = null;
		ResultSet rs = null, rs2 = null;
		try {
			s1 = connection.prepareStatement(q1);
			s2 = connection.prepareStatement(q2);
			s1.setInt(1, id);
			rs = s1.executeQuery();
			while (rs.next()) {
				s2.setInt(1, rs.getInt("song_id"));
				rs2 = s2.executeQuery();
				if (rs2.next()) {
					songList.add(new Song(rs.getInt("song_id"), rs2.getString("name"), rs2.getString("video_link")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (rs2 != null) {
					rs2.close();
				}
				if (s1 != null) {
					s1.close();
				}
				if (s2 != null) {
					s2.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return songList;
	}
}
