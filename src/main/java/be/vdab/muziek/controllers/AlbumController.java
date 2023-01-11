package be.vdab.muziek.controllers;

import be.vdab.muziek.exceptions.AlbumNietGevondenException;
import be.vdab.muziek.forms.ScoreForm;
import be.vdab.muziek.services.AlbumService;
import be.vdab.muziek.services.ArtiestService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("album")
public class AlbumController {
    private final AlbumService albumService;
    private final ArtiestService artiestService;

    public AlbumController(AlbumService albumService, ArtiestService artiestService) {
        this.albumService = albumService;
        this.artiestService = artiestService;
    }

    @GetMapping("{id}")
    public ModelAndView findById(@PathVariable long id) {
        var modelandview = new ModelAndView("album");
        albumService.findById(id).ifPresent(album ->
                {
                    modelandview.addObject("album", album);
                    modelandview.addObject(new ScoreForm(album.getScore()));
                }
        );
        return modelandview;
    }

    @PostMapping("{id}/score")
    public ModelAndView wijzigScore(@PathVariable long id, @Valid ScoreForm form, Errors errors,
                                    RedirectAttributes redirect) {
        if (errors.hasErrors()) {
            var modelAndView = new ModelAndView("album");
            albumService.findById(id).ifPresent(album -> modelAndView.addObject(album));
            return modelAndView;
        }
        try {
            albumService.setScore(id, form.score());
            redirect.addAttribute("id", id); ///
            return new ModelAndView("redirect:/album/{id}");
        } catch (
                AlbumNietGevondenException ex) {
            return new ModelAndView("album");
        }
    }

    @GetMapping("jaar/{jaar}")
    public ModelAndView jaar(@PathVariable int jaar) {
        return new ModelAndView("jaar", "alleAlbums", albumService.findByJaar(jaar));
    }

    @GetMapping("artiest/{artiestId}")
    public ModelAndView artiest(@PathVariable long artiestId) {
        var modelAndView = new ModelAndView("artiest", "alleAlbums",
                albumService.findByArtiestId(artiestId));
        modelAndView.addObject("artiestNaam", artiestService.findById(artiestId).get().getNaam());
        return modelAndView;
    }
}
