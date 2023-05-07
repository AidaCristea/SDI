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
import { RecordLable } from "../../models/RecordLable";

import { BACKEND_API_URL } from "../../constants";
  
  export const RecordLblsShowAll = () => {
    const [loading, setLoading] = useState(false);
    const [recordLbls, setRecordLbl] = useState<RecordLable[]>([]);
    const [currentPage, setCurrentPage]=useState(0)
    const [pageSize, setPageSize] = useState(100);
    const [totalRecLbls, setTotalRecLbls] =useState(0)


    //useEffect(() => {
      //fetch(GlobalURL + "/recordLbls")
      //fetch("api/recordLbls")
    //  fetch(GlobalURL + "/recordLbls/page/{page}/size/{size}")
    //    .then((res) => res.json())
    //    .then((data) => {
    //      console.log(data);
    //      setRecordLbl(data);
    //      setLoading(false);
    //    });
    //}, []);


    // useEffect(() => {
    //   setLoading(true);
    //   Promise.all([
    //     //fetch(GlobalURL + "/recordLbls/countAll").then((response) => response.json()),
    //     //fetch(GlobalURL + "/recordLbls/page/${page}/size/${size}").then((response) => response.json())
    //     fetch(`${BACKEND_API_URL}/recordLbls/countAll`).then((response) => response.json()),
    //     fetch(`${BACKEND_API_URL}/recordLbls/page/${currentPage}/size/${pageSize}`).then((response) => response.json())
    //   ])
    //   .then(([countAll, data]) => {
    //     setTotalRecLbls(countAll);
    //     setRecordLbl(data);
    //     setLoading(false);
    //   });
    // }, [currentPage, pageSize]);

    useEffect(() => {
      setLoading(true);

      const fetchRecLbl = () => {
        fetch(`${BACKEND_API_URL}/recordLbls/countAll`)
        .then((response) => response.json())
        .then((count) => {
          fetch(`${BACKEND_API_URL}/recordLbls/page/${currentPage}/size/${pageSize}`)
          .then((response) => response.json())
          .then((data) => {
            setTotalRecLbls(count);
            setRecordLbl(data);
            setLoading(false);
          });
        })
        .catch((error) => {
          console.error(error);
          setLoading(false);
        });
      };
      fetchRecLbl();
    }, [currentPage, pageSize]);



    const sortRecordLables = () => {
      const sortedRecordLables = [...recordLbls].sort((a: RecordLable, b: RecordLable) => {
          if (a.price < b.price) {
              return -1;
          }
          if (a.price > b.price) {
              return 1;
          }
          return 0;
      })
      console.log(sortedRecordLables);
      setRecordLbl(sortedRecordLables);
  }

  const handlePreviousPage = () => {
    if(currentPage>0)
    {
      setCurrentPage(currentPage-1);
    }
  };

  const handleNextPage = () => {
    setCurrentPage(currentPage+1);
  };

  
    
    return (
      <Container>
        <h1>All Record Lables</h1>
  
        {loading && <CircularProgress />}
        {!loading && recordLbls.length === 0 && <p>No record lables found</p>}
        {!loading && (
          <div style={{display:'flex', alignItems:'center'}}>
            <IconButton component={Link} sx={{marginRight: 0 }} to={`/recordLbls/add`}>
              <Tooltip title="Add a new record lable" arrow>
                <AddIcon color="primary" />
              </Tooltip>
            </IconButton>
          </div>
        )}

        {!loading && (
            <div style ={{display: "flex", alignItems:"center"}}>
                <Button sx={{color:"purple", border: "1px solid purple", borderColor: "purple"}} onClick={sortRecordLables} >
                    Sort record lables
                </Button>
                <Button
                  sx={{color:"black"}}
                  disabled={currentPage===0}
                  onClick={handlePreviousPage}>
                    Previous Page
                </Button>
                <Button
                 sx={{color:"black"}} onClick={handleNextPage}>
                  Next Page
                 </Button>

                 <Box mx={2} display="flex" alignItems="center">
                  Page {currentPage+1} of {Math.ceil(totalRecLbls/pageSize)}
                 </Box>
            </div>
            )}

  
        {!loading && recordLbls.length > 0 && (
          <TableContainer component={Paper}>
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
              <TableHead>
                <TableRow>
                  <TableCell>#</TableCell>
                  <TableCell align="center">Name</TableCell>
                  <TableCell align="center">Address</TableCell>
                  <TableCell align="right">Price</TableCell>
                  <TableCell align="center">Review</TableCell>
                  <TableCell align="center">Nr collaborations</TableCell>
                  <TableCell align="center">Operations</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {recordLbls.map((recLbl, index) => (
                  <TableRow key={recLbl.idRecLbl}>
                    <TableCell component="th" scope="row">
                      {index + 1}
                    </TableCell>
                    <TableCell component="th" scope="row">
                      <Link
                        to={`/recordLbls/${recLbl.idRecLbl}`}
                        title="View record lables details"
                      >
                        {recLbl.nameRl}
                      </Link>
                    </TableCell>
  
                    <TableCell align="right">{recLbl.address}</TableCell>
                    <TableCell align="right">{recLbl.price}</TableCell>
                    <TableCell align="center">{recLbl.review}</TableCell>
                    <TableCell align="center">{recLbl.nrCollaborations}</TableCell>
                    
                    <TableCell align="right">
                      <IconButton
                        component={Link}
                        sx={{ mr: 3 }}
                        to={`/recordLbls/${recLbl.idRecLbl}`}
                      >
                        <Tooltip title="View record lable details" arrow>
                          <ReadMoreIcon color="primary" />
                        </Tooltip>
                      </IconButton>
  
                      <IconButton
                        component={Link}
                        sx={{ mr: 3 }}
                        to={`/recordLbls/${recLbl.idRecLbl}/edit`}
                        title="Edit record lable details"
                      >
                        <EditIcon />
                      </IconButton>
  
                      <IconButton
                        component={Link}
                        sx={{ mr: 3 }}
                        to={`/recordLbls/${recLbl.idRecLbl}/delete`}
                        title="Delete record lable"
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
          sx={{color:"black"}}
          disabled={currentPage===0}
          onClick={handlePreviousPage}>
            Previous Page
          </Button>

        <Button sx={{color:"black"}} onClick={handleNextPage}>
          Next Page
        </Button>
      </Container>
    );
  };
  