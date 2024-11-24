package com.kodnest.tunehub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.kodnest.tunehub.entity.Song;
import com.kodnest.tunehub.service.SongService;

@Controller
public class SongController {

    @Autowired
    SongService songService;

    // Get mapping for adding song, ensure the user is authenticated and has the 'admin' role
//    @GetMapping("/addsongs")
//    public String showAddSongForm(HttpSession session, Model model) {
//        String email = (String) session.getAttribute("email");
//        String role = (String) session.getAttribute("role");  // Retrieve the role from session
//        System.out.println("Session - Email: " + email + " | Role: " + role);
//
//        if (email == null || role == null) {
//            System.out.println("User not authenticated. Redirecting to login...");
//            return "redirect:/login";  // If session is missing email or role, redirect to login
//        }
//
//        // Ensure role comparison takes 'ROLE_' into account
//        if (!role.equals("ROLE_admin")) {
//            System.out.println("User is not an admin. Redirecting to home page...");
//            return "redirect:/";  // Redirect to home or another page for non-admin users
//        }
//
//        System.out.println("Rendering the Add Song form for " + role);
//        model.addAttribute("song", new Song());
//        return "Song";  // Render the addSong form
//    }

    // Post mapping for adding a song
    @PostMapping("/addsongs")
    public String addSong(@ModelAttribute Song song) {
        String name = song.getName();
        System.out.println("Attempting to add song: " + name);

        boolean songExists = songService.songExists(name);
        if (songExists == false) {
            songService.saveSong(song);
            System.out.println("Song added successfully: " + name);
        } else {
            System.out.println("Duplicate song detected: " + name);
        }
        return "adminhome";  // Redirect to admin home after adding the song
    }

    // Get mapping for playing songs (if premium user)
    @GetMapping("/playsongs")
    public String playsongs(Model model) {
        boolean Premium = true;  // This should be dynamically determined based on the user’s subscription status
        if (Premium) {
            List<Song> songList = songService.fetchAllSongs();
            model.addAttribute("songs", songList);
            return "viewSongs";  // Display songs
        } else {
            return "pay";  // Redirect to payment page if not a premium user
        }
    }

    // Get mapping for viewing songs
    @GetMapping("/viewsongs")
    public String viewSongs(Model model) {
        List<Song> songList = songService.fetchAllSongs();
        model.addAttribute("songs", songList);
        return "viewSongs";  // Render view for songs
    }
}
