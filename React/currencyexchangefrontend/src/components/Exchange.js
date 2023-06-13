import React, { useEffect, useState } from 'react';
import TextField from '@mui/material/TextField';
import { Container, Paper, Button } from '@mui/material';


export default function Exchange() {
  const paperStyle = { padding: '50px 20px', width: 600, margin: '20px auto' }
  const [currencyTo, setCurrencyTo] = useState('')
  const [currencyFrom, setCurrencyFrom] = useState('')
  const [exchangeDate, setExchangeDate] = useState('')
  const getURL = "http://localhost:8080/exchange/v1/get/2023-04-23/USD/EUR"
  const updatedURL = getURL.replace("2023-04-23", exchangeDate).replace("USD", currencyFrom).replace("EUR", currencyTo)


  const [exchanges, setExchanges] = useState([])

  // useEffect(()=>{
  //   fetch(updatedURL)
  //   .then(res=>res.json())
  //   .then ((result) => {
  //     setExchanges(result);
  //   }
  //   )
  // })
  const fetchData = async () => {
    try {
      const response = await fetch(updatedURL);
      const data = await response.json();
      return data;
    }
    catch (error) {
      console.error("Error fetching data: ", error);
      throw error;
    }
  };
  const handleClick = () => {
    const fetchExchanges = async () => {
      const fetchExchanges = await fetchData();
      setExchanges(fetchExchanges);
    };
    fetchExchanges();

    console.log(exchanges);
  }

  return (
    <Container>
      <Paper elevation={3} style={paperStyle}>
        <h1 style={{ color: "blue" }}><u>Get Exchange</u></h1>


        <TextField id="outlined-basic" label="Currency from ISO" variant="outlined" fullWidth
          value={currencyFrom}
          onChange={(e) => setCurrencyFrom(e.target.value)}
        />
        <TextField id="outlined-basic" label="Currency to ISO" variant="outlined" fullWidth
          value={currencyTo}
          onChange={(e) => setCurrencyTo(e.target.value)}
        />
        <TextField id="outlined-basic" label="Exchange date" variant="outlined" fullWidth
          value={exchangeDate}
          onChange={(e) => setExchangeDate(e.target.value)}
        />
        <Button variant="contained" onClick={handleClick}>Submit</Button>

        <h1 >Exchanges</h1>
        <Paper elevation={3} style={paperStyle}>
          {
            <Paper elevation={6} style={{ margin: "10px", padding: "15px", textAlign: "left" }} key={exchanges?.id}>
              {exchanges && exchanges.exchangeInfo ? (
                <>
                  {exchanges.exchangeInfo.length > 0 ? (
                    <>
                      CurrencyTo: {exchanges.exchangeInfo[0].currencyTo}{'\n'}
                      CurrencyFrom: {exchanges.exchangeInfo[0].currencyFrom}{'\n'}
                      ExchangeRate: {exchanges.exchangeInfo[0].rate}{'\n'}
                    </>
                  ) : (
                    <div>No data available</div>
                  )}
                </>
              ) : (
                <div>Loading...</div>
              )}

            </Paper>
          }

        </Paper>

      </Paper>
    </Container>
  );
}
