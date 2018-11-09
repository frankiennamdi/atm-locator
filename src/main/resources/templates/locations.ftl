<html>
<head>
  <title>Atm Listings</title>
  <style type="text/css" media="screen">

    .address {
      width: 500px;
      overflow: hidden;
      border: black 1px solid;
    }

    .container {
      width: 500px;
      overflow: hidden;
    }

    .left {
      float: left;
      width: 100px;
      margin: 0 5%;
    }

    .right {
      float: left;
      width: 200px;
      margin: 0 5%;
    }

    .horizontal-line {
      width: 100%;
      height: 5px;
      margin: 0 auto;
    }
  </style>
</head>
<body>
<h1>Atm Listings</h1>
<#list atmList as atm>
<div class="address">
  <div class="container">
    <div class="left">Address:</div>
    <div class="right">${atm.addressStreet}</div>
  </div>
  <div class="container">
    <div class="left">House #:</div>
    <div class="right">${atm.addressHouseNumber}</div>
  </div>
  <div class="container">
    <div class="left">Postal Code:</div>
    <div class="right">${atm.addressPostalCode}</div>
  </div>
  <div class="container">
    <div class="left">City:</div>
    <div class="right">${atm.addressCity}</div>
  </div>
  <div class="container">
    <div class="left">Lat:</div>
    <div class="right">${atm.addressGeolocationLat}</div>
  </div>
  <div class="container">
    <div class="left">Lng:</div>
    <div class="right">${atm.addressGeolocationLng}</div>
  </div>
  <div class="container">
    <div class="left">Type:</div>
    <div class="right">${atm.type}</div>
  </div>
  <div class="horizontal-line"></div>
</div>
</#list>
</body>
</html>