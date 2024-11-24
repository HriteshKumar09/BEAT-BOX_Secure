package com.kodnest.tunehub.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kodnest.tunehub.entity.Playlist;
import com.kodnest.tunehub.repository.PlaylistRepository;
import com.kodnest.tunehub.service.PlaylistService;

@Service
public class PlaylistServiceImplementation implements PlaylistService 
{
@Autowired
PlaylistRepository playlistRepsotiroy;

@Override
public void addPlaylist(Playlist playlist) 
	{
	Playlist exisplaylist=playlistRepsotiroy.findByName(playlist.getName());
	if(exisplaylist==null)
	{	
	playlistRepsotiroy.save(playlist);
	}
	else
	{
		System.out.println("Playlist already exists!");
	}
}
@Override
public List<Playlist>fetchAllPlaylist() 
{
	List<Playlist>playlists=playlistRepsotiroy.findAll();
	return playlists;
}
}

