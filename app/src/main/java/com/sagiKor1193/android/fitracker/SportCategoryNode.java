package com.sagiKor1193.android.fitracker;


public class SportCategoryNode {
    //Scores
    private double _cubesData;
    private double _aeroData;
    private int _absData;
    private int _jumpData;
    private double _handsData;
    //Result;
    private int _cubesRes;
    private int _absRes;
    private int _aeroRes;
    private int _handsRes;
    private int _jumpRes;
    //Next
    SportCategoryNode _next;


    public SportCategoryNode(double cubes, int cubesRes, double aero, int aeroRes, int abs, int absRes,
                             int jump, int jumpRes , double hands, int handsRes){
        _cubesData = cubes;
        _cubesRes = cubesRes;

        _aeroData = aero;
        _aeroRes = aeroRes;

        _absData = abs;
        _absRes = absRes;

        _jumpData = jump;
        _jumpRes = jumpRes;


        _handsData = hands;
        _handsRes = handsRes;

   }
    public double get_cubesData() {
        return _cubesData;
    }

    public void set_cubesData(double _cubesData) {
        this._cubesData = _cubesData;
    }

    public double get_aeroData() {
        return _aeroData;
    }

    public void set_aeroData(double _aeroData) {
        this._aeroData = _aeroData;
    }

    public int get_absData() {
        return _absData;
    }

    public void set_absData(short _absData) {
        this._absData = _absData;
    }

    public int get_jumpData() {
        return _jumpData;
    }

    public void set_jumpData(short _jumpData) {
        this._jumpData = _jumpData;
    }

    public double get_handsData() {
        return _handsData;
    }

    public void set_handsData(double _handsData) {
        this._handsData = _handsData;
    }

    public int get_cubesRes() {
        return _cubesRes;
    }

    public void set_cubesRes(short _cubesRes) {
        this._cubesRes = _cubesRes;
    }

    public int get_absRes() {
        return _absRes;
    }

    public void set_absRes(short _absRes) {
        this._absRes = _absRes;
    }

    public int get_aeroRes() {
        return _aeroRes;
    }

    public void set_aeroRes(short _aeroRes) {
        this._aeroRes = _aeroRes;
    }

    public int get_handsRes() {
        return _handsRes;
    }

    public void set_handsRes(short _handsRes) {
        this._handsRes = _handsRes;
    }

    public int get_jumpRes() {
        return _jumpRes;
    }

    public void set_jumpRes(short _jumpRes) {
        this._jumpRes = _jumpRes;
    }
}
