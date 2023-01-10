package be.vdab.muziek.controllers;

import be.vdab.muziek.services.AlbumService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    private final AlbumService albumService;

    public IndexController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index", "alleAlbums", albumService.findAll());
    }
}

