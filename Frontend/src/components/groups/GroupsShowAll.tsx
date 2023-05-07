import {
    TableContainer,
    Paper,
    Table,
    TableHead,
    TableRow,
    TableCell,
    TableBody,
    CircularProgress,
    Container,
    IconButton,
    Tooltip,
    Button,
    Box,
  } from "@mui/material";
  
  import { useEffect, useState } from "react";
  import { Singer } from "../../models/Singer";
  
  import { Link } from "react-router-dom";
  import AddIcon from "@mui/icons-material/Add";
  import ReadMoreIcon from "@mui/icons-material/ReadMore";
  import EditIcon from "@mui/icons-material/Edit";
  import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
  
  import { BACKEND_API_URL } from "../../constants";
import { Group } from "../../models/Group";
  
  export const GroupShowAll = () => {
    const [loading, setLoading] = useState(false);
    const [groups, setGroups] = useState<Group[]>([]);
    const [pageSize, setPageSize] = useState(100);
    const [totalgroups, setTotalGroups] = useState(0);
    const [currentPage, setCurrentPage] = useState(0);
  
    // useEffect(() => {
    //   fetch( `${BACKEND_API_URL}/singers`)
    //     .then((res) => res.json())
    //     .then((data) => {
    //       console.log(data);
    //       setSingers(data);
    //       setLoading(false);
    //     });
    // }, []);
  
    // useEffect(() => {
    //   setLoading(true);
  
    //   const fetchRecLbl = () => {
    //     fetch(`${BACKEND_API_URL}/singers/countAll`)
    //     .then((response) => response.json())
    //     .then((count) => {
    //       fetch(`${BACKEND_API_URL}/singers/page/${currentPage}/size/${pageSize}`)
    //       .then((response) => response.json())
    //       .then((data) => {
    //         setTotalSingers(count);
    //         setSingers(data);
    //         setLoading(false);
    //       });
    //     })
    //     .catch((error) => {
    //       console.error(error);
    //       setLoading(false);
    //     });
    //   };
    //   fetchRecLbl();
    // }, [currentPage, pageSize]);
  
    //  const fetchSingers = async () => {
    //    setLoading(true);
    //    const response = await fetch(
    //      `${BACKEND_API_URL}/singers/page/${currentPage}/size/${pageSize}`
    //    );
    //    const count = await fetch(
    //     `${BACKEND_API_URL}/singers/countAll`
    //   );
  
    //    const {next, previous, results} = await response.json();
    //    const countt = await count.json();
    //    console.log(countt);
    //    console.log(response);
    //    setSingers(results);
    //    setTotalSingers(countt);
    //    setLoading(false);
    //  };
  
    const fetchGroups = () => {
      fetch(`${BACKEND_API_URL}/groups/countAll`)
        .then((response) => response.json())
        .then((count) => {
          fetch(`${BACKEND_API_URL}/groups/page/${currentPage}/size/${pageSize}`)
            .then((response) => response.json())
            .then((data) => {
              setTotalGroups(count);
              setGroups(data);
              setLoading(false);
            });
        })
        .catch((error) => {
          console.error(error);
          setLoading(false);
        });
    };
  
    useEffect(() => {
      fetchGroups();
    }, [currentPage, pageSize]);
  
    const handlePreviousPage = () => {
      if (currentPage > 0) {
        setCurrentPage(currentPage - 1);
      }
    };
  
    const handleNextPage = () => {
      setCurrentPage(currentPage + 1);
    };
  
    return (
      <Container>
        <h1>All groups</h1>
  
        {loading && <CircularProgress />}
        {!loading && groups.length === 0 && <p>No groups found</p>}
        {!loading && (
          <div style={{ display: "flex", alignItems: "center" }}>
            <IconButton
              component={Link}
              sx={{ marginRight: 0 }}
              to={`/groups/add`}
            >
              <Tooltip title="Add a new group" arrow>
                <AddIcon color="primary" />
              </Tooltip>
            </IconButton>
          </div>
        )}
  
        {!loading && (
          <div style={{ display: "flex", alignItems: "center" }}>
            <Button
              sx={{ color: "black" }}
              disabled={currentPage === 0}
              onClick={handlePreviousPage}
            >
              Previous Page
            </Button>
            <Button
              disabled={groups.length < pageSize}
              sx={{ color: "black" }}
              onClick={handleNextPage}
            >
              Next Page
            </Button>
  
            <Box mx={2} display="flex" alignItems="center">
              Page {currentPage + 1} of {Math.ceil(totalgroups / pageSize)}
            </Box>
          </div>
        )}
  
        {!loading && groups.length > 0 && (
          <TableContainer component={Paper}>
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
              <TableHead>
                <TableRow>
                  <TableCell>#</TableCell>
                  <TableCell align="center">Group name</TableCell>
                  <TableCell align="center">Members</TableCell>
                  <TableCell align="right">Date formed</TableCell>
                  
                  <TableCell align="center">Music Specialization</TableCell>
                  <TableCell align="right">Review</TableCell>
                  <TableCell align="center">Description</TableCell>
                  <TableCell align="center">Operations</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {groups.map((group: Group, index) => (
                  <TableRow key={group.idGroup}>
                    <TableCell component="th" scope="row">
                      {index + 1}
                    </TableCell>
                    <TableCell component="th" scope="row">
                      <Link
                        to={`/groups/${group.idGroup}`}
                        title="View group details"
                      >
                        {group.nameGr}
                      </Link>
                    </TableCell>
  
                    <TableCell align="right">{group.members}</TableCell>
                    <TableCell align="right">{group.dateFormed}</TableCell>
                    <TableCell align="center">{group.musicSpecialization}</TableCell>
                    <TableCell align="right">{group.review}</TableCell>
                    <TableCell align="right">{group.description}</TableCell>
  
                    <TableCell align="right">
                      <IconButton
                        component={Link}
                        sx={{ mr: 3 }}
                        to={`/groups/${group.idGroup}`}
                      >
                        <Tooltip title="View group details" arrow>
                          <ReadMoreIcon color="primary" />
                        </Tooltip>
                      </IconButton>
  
                      <IconButton
                        component={Link}
                        sx={{ mr: 3 }}
                        to={`/groups/${group.idGroup}/edit`}
                        title="Edit group details"
                      >
                        <EditIcon />
                      </IconButton>
  
                      <IconButton
                        component={Link}
                        sx={{ mr: 3 }}
                        to={`/groups/${group.idGroup}/delete`}
                        title="Delete group"
                      >
                        <DeleteForeverIcon sx={{ color: "red" }} />
                      </IconButton>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        )}
        <Button
          sx={{ color: "black" }}
          disabled={currentPage === 0}
          onClick={handlePreviousPage}
        >
          Previous Page
        </Button>
  
        <Button
          sx={{ color: "black" }}
          disabled={groups.length < pageSize}
          onClick={handleNextPage}
        >
          Next Page
        </Button>
      </Container>
    );
  };
  