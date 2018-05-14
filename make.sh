#!/usr/bin/env sh

lein do clean, with-profile +prod sass4clj once, cljsbuild once min, ring uberjar
