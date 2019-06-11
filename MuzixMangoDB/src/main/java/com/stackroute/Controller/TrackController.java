package com.stackroute.Controller;

import com.stackroute.Domain.Track;
import com.stackroute.Exceptions.GlobalException;
import com.stackroute.Exceptions.TrackNotFoundException;
import com.stackroute.Service.TrackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class TrackController {
    TrackService trackService;

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @PostMapping(value = "track")
    public ResponseEntity<Track> saveTrack(@RequestBody Track track)
    {
        ResponseEntity responseEntity;
        try {
            Track track1=trackService.savingTrack(track);
            responseEntity=new ResponseEntity<String>("Successfully Added Track", HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            responseEntity=new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
            e.printStackTrace();
        }
        return responseEntity;
    }
    @GetMapping(value ="track")
    public ResponseEntity<List<Track>> getAllTracks()
    {
        List<Track> track1=trackService.getAllTracks();
        return new ResponseEntity<List<Track>>(track1,HttpStatus.OK);
    }
    @RequestMapping(value="track/{id}",method=RequestMethod.PUT)
    public ResponseEntity<Track> updateTrack(@RequestBody Track track) throws TrackNotFoundException
    {
        ResponseEntity responseEntity;
        try {
            Track track1=trackService.updateComment(track);
            return new ResponseEntity<Track>(track1,HttpStatus.OK);
        }
        catch (Exception | GlobalException e)
        {
            responseEntity=new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
            e.printStackTrace();
        }
        return responseEntity;
    }

    @DeleteMapping("track/{id}")
    public ResponseEntity<String> deleteTrack(@RequestBody Track track)
    {
        ResponseEntity responseEntity;
        try {
            boolean answer=trackService.deleteTrack(track);
            return new ResponseEntity<String>("Successfully deleted", HttpStatus.OK);
        }
        catch (Exception e)
        {
            responseEntity=new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
            e.printStackTrace();
        }
        return responseEntity;
    }






}
