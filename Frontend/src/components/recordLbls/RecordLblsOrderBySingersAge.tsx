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

import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import { useEffect, useState } from "react";

import { Link } from "react-router-dom";
import AddIcon from "@mui/icons-material/Add";
import { RecordLblsForAvg } from "../../models/RecordLblsForAge";

import { BACKEND_API_URL } from "../../constants";


export const RecordLblsOrderBySingersAge = () => {
    const[loading, setLoading] = useState(true)
    const [records, setRecords] = useState([]);
    const [pageSize, setPageSize] = useState(100);
    const [totalRecLbls, setTotalRecLbls] =useState(0)
    const [currentPage, setCurrentPage]=useState(0)

    // useEffect(() => {
    // fetch( `${BACKEND_API_URL}/average-age`)
    //     .then(res => res.json())
    //     .then(data => {setRecords(data); setLoading(false);})
    // }, []);

    // console.log(records);


    useEffect(() => {
        setLoading(true);
  
        const fetchRecLbl = () => {
          fetch(`${BACKEND_API_URL}/recordLbls/countAll`)
          .then((response) => response.json())
          .then((count) => {
            fetch(`${BACKEND_API_URL}/average-age/page/${currentPage}/size/${pageSize}`)
            .then((response) => response.json())
            .then((data) => {
              setTotalRecLbls(count);
              setRecords(data);
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
        <h1>All Record Lables Ordered By The Average Singers Age</h1>

        {loading && <CircularProgress />}

        {!loading && records.length == 0 && <div>No records found</div>}

        {!loading && (
            <div style ={{display: "flex", alignItems:"center"}}>
                
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

        {!loading && records.length > 0 && (
           
            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 800 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>#</TableCell>
                            <TableCell align="center">Name</TableCell>
                            <TableCell align="center">Address</TableCell>
                            <TableCell align="center">Price</TableCell>
                            <TableCell align="center">Review</TableCell>
                            <TableCell align="center">Nr Collaborations</TableCell>
                            <TableCell align="center">Average Singers Age</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {records.map((records:RecordLblsForAvg, index) => (
                            <TableRow key={records.id}>
                                <TableCell component="th" scope="row">
                                    {index + 1}
                                </TableCell>
                                <TableCell align="center">{records.nameRl}</TableCell>
                                <TableCell align="center">{records.address}</TableCell>
                                <TableCell align="center">{records.price}</TableCell>
                                <TableCell align="center">{records.review}</TableCell>
                                <TableCell align="center">{records.nrCollaborations}</TableCell>
                                <TableCell align="center">{records.avgSingerAge}</TableCell>
                            </TableRow>
                        ))}
                </TableBody>
                </Table>
            </TableContainer>
        )
        }
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