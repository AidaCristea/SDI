import React, { useState } from "react";
import reactLogo from "./assets/react.svg";
import viteLogo from "/vite.svg";
import "./App.css";
import { SingerShowAll } from "./components/singers/SingerShowAll";
import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import { AppMenu } from "./components/AppMenu";
import { AppHome } from "./components/AppHome";
import { SingerDetails } from "./components/singers/SingerDetails";
import { SingerDelete } from "./components/singers/SingerDelete";
import { SingerAdd } from "./components/singers/SingerAdd";
import { RecordLblsShowAll } from "./components/recordLbls/RecordLblsShowAll";
import { RecordLblsDetails } from "./components/recordLbls/RecordLblsDetails";
import { RecordLblsDelete } from "./components/recordLbls/RecordLblsDelete";
import { RecordLblsAdd } from "./components/recordLbls/RecordLblsAdd";
import { RecordLblsUpdate } from "./components/recordLbls/RecordLblsUpdate";
import { RecordLblsOrderBySingersAge } from "./components/recordLbls/RecordLblsOrderBySingersAge";
import { SingerUpdate } from "./components/singers/SingerUpdate";
import { GroupShowAll } from "./components/groups/GroupsShowAll";
import { GroupDetails } from "./components/groups/GroupsDetails";
import { GroupsAdd } from "./components/groups/GroupsAdd";
import { GroupDelete } from "./components/groups/GroupsDelete";
import { GroupUpdate } from "./components/groups/GroupsUpdate";
import { AlbumShowAll } from "./components/albums/AlbumsShowAll";
import { AlbumDetails } from "./components/albums/AlbumsDetails";
import { AlbumAdd } from "./components/albums/AlbumsAdd";
import { AlbumDelete } from "./components/albums/AlbumsDelete";
import { AlbumUpdate } from "./components/albums/AlbumsUpdate";

function App() {
  //const [count, setCount] = useState(0);

  return (
    <React.Fragment>
      <Router>
        <AppMenu />
        <Routes>
          <Route path="/" element={<AppHome />} />
          <Route path="/singers" element={<SingerShowAll />} />
          <Route path="/singers/:singerId" element={<SingerDetails />} />
          <Route path="/singers/:singerId/delete" element={<SingerDelete />} />
          <Route path="/singers/add" element={<SingerAdd />} />
          <Route path="/singers/:singerId/edit" element={<SingerUpdate />}/>
        </Routes>
        <Routes>
          <Route path="/recordLbls" element={<RecordLblsShowAll />} />
          <Route path="/recordLbls/:recLblId" element={<RecordLblsDetails />} />
          <Route path="/recordLbls/:id/delete" element={<RecordLblsDelete />} />
          <Route path="/recordLbls/add" element={<RecordLblsAdd />} />
          <Route path="/recordLbls/:recId/edit" element={<RecordLblsUpdate />} />
          <Route path="/average-age" element={<RecordLblsOrderBySingersAge />} />
        </Routes>
        <Routes>
          <Route path="/groups" element={<GroupShowAll />} />
          <Route path="/groups/:groupId" element={<GroupDetails />} />
          <Route path="/groups/add" element={<GroupsAdd />} />
          <Route path="/groups/:groupId/delete" element={<GroupDelete />} />
          <Route path="/groups/:groupId/edit" element={<GroupUpdate />}/>
        </Routes>
        <Routes>
          <Route path="/albums" element={<AlbumShowAll />} />
          <Route path="/albums/:albumId" element={<AlbumDetails />} />
          <Route path="/albums/add" element={<AlbumAdd />} />
          <Route path="/albums/:albumId/delete" element={<AlbumDelete />} />
          <Route path="/albums/:albumId/edit" element={<AlbumUpdate />}/>
         
        </Routes>


      </Router>
    </React.Fragment>
  );
}

export default App;
