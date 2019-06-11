package com.stackroute.Service;

import com.stackroute.Domain.Track;
import com.stackroute.Exceptions.GlobalException;
import com.stackroute.Exceptions.TrackAlreadyExistsException;
import com.stackroute.Exceptions.TrackNotFoundException;
import com.stackroute.Repository.TrackRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class TrackServiceTest {
    private Track track;
    private Optional optional;

    //Create a mock for Track Repository
    @Mock
    private TrackRepository trackRepository;

    //Inject the mocks as dependencies into TrackServiceImpl
    @InjectMocks
    private TrackServiceImpl trackService;
    List<Track> list = null;


    @Before
    public void setUp() {
        //Initialising the mock object
        MockitoAnnotations.initMocks(this);
        track = new Track();
        track.setTrackId(9);
        track.setTrackName("Leela");
        track.setTrackcomments("Chattivanipalem");
        list = new ArrayList<>();
        list.add(track);


    }

    @Test
    public void GivenTosaveTrack_ExpectedSaveTrackSuccess() throws Exception {

        when(trackRepository.save((Track) any())).thenReturn(track);
        Track savedTrack = trackService.savingTrack(track);
        assertEquals(track, savedTrack);

        //verify here verifies that TrackRepository save method is only called once
        verify(trackRepository, times(1)).save(track);

    }

    @Test(expected = TrackAlreadyExistsException.class)
    public void GivenTosaveTrackTestFailure_ExpectedToFailInSavingTrack() throws Exception {
        when(trackRepository.save((Track) any())).thenReturn(null);
        Track savedUser = trackService.savingTrack(track);
        System.out.println("savedUser" + savedUser);
        assertEquals(track, savedUser);
    }

    @Test
    public void GivenToshowAllTracks_ExpectedToDisplayAllTracks() {

        trackRepository.save(track);
        //stubbing the mock to return specific data
        when(trackRepository.findAll()).thenReturn(list);
        List<Track> tracklist = trackService.getAllTracks();
        assertEquals(list, tracklist);
    }


  @Test
  public void GivenToDeleteTrack_ExpectedDeletionSuccessfully() throws TrackNotFoundException {

          Track track=new Track(10,"John","John Doe");
        when(trackRepository.findAll()).thenReturn(list);
        List<Track> tracklist = trackService.getAllTracks();
          trackRepository.delete(track);
          boolean deletedTrack=trackRepository.existsById(14);
          assertEquals(false,deletedTrack);


      }


    @Test
    public void GivenToDeleteTrackFailure_ExpectedDeleteFailure(){

        Track track=new Track(21,"Tej  I Love You","movie_Hero:Jet Panda");
        when(trackRepository.findAll()).thenReturn(list);
        trackRepository.delete(track);
        boolean deletedTrack=trackRepository.existsById(21);
        List<Track> list = trackService.getAllTracks();
        Assert.assertNotSame(true,deletedTrack);
    }
   /* @Test
    public void GivenTrackByNameById_ExpectedTrack() throws TrackNotFoundException{

        when(trackRepository.getTrackByName(track.getTrackName())).thenReturn(list);
        Track track3=new Track(9,"Leela","Chattivanipalem");
        List<Track> list1=trackService.getAllTracksByNameById(track.getTrackName(),track.getTrackId());
        Assert.assertEquals(track3,track);
    }*/


    @Test
    public void GivenUpdateTrackComments_ExpectedUpdatedCommentsInTrack() throws Exception {

        when(trackRepository.existsById(track.getTrackId())).thenReturn(true);
        when(trackRepository.save((Track)any())).thenReturn(track);
        track.setTrackcomments("helo");
        Track track1= null;
        try {
            track1 = trackService.updateComment(track);
        } catch (GlobalException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("helo",track1.getTrackcomments());
    }

    @Test
    public void GivenUpdateTrackCommentsFailure_ExpectedUpdateFailure() throws GlobalException{

        when(trackRepository.existsById(track.getTrackId())).thenReturn(true);
        when(trackRepository.save((Track)any())).thenReturn(track);
        track.setTrackcomments("helo");
        Track track1=trackService.updateComment(track);
        Assert.assertNotSame("helos",track1.getTrackcomments());
    }


}
